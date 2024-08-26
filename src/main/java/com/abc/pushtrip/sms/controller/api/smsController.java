package com.abc.pushtrip.sms.controller.api;

import ch.qos.logback.classic.util.LogbackMDCAdapter;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


@RestController
@RequestMapping("/api")
public class smsController {

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    final DefaultMessageService messageService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TEMP_PASSWORD_LENGTH = 8;

    public smsController() {

        this.messageService = NurigoApp.INSTANCE.initialize("키");
    }


    //아이디찾기 전화번호인증
    @PostMapping("/send-message")
    public ResponseEntity<?> sendOne(@RequestParam String tel) {
        try {
            User user = userRepository.findByTel(tel);
            if (user != null) {
                Message message = new Message();
                String pushtrip = "발신자";
                String code = String.format("%06d", new Random().nextInt(1000000));
                LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);



                user.setVerificationCode(code);
                user.setVerificationCodeExpiry(expiryDate);
                userRepository.save(user);



                message.setFrom(pushtrip);
                message.setTo(tel);
                message.setText("pushtrip인증번호: " + code + "\n\n5분안에 입력해주세요.");

                SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
                System.out.println(response);

                return ResponseEntity.ok().body(Map.of("success", true));
            } else {

                return ResponseEntity.ok().body(Map.of("success", false));

            }

        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }


    @PostMapping("/verify-phone-code")
    public ResponseEntity<?> verifyphoneCode(@RequestParam String tel, @RequestParam String phoneCode) {

        try {
            User user = userRepository.findByTel(tel);
            if (user != null && user.getVerificationCode() != null
                    && user.getVerificationCode().equals(phoneCode)
                    && LocalDateTime.now().isBefore(user.getVerificationCodeExpiry())) {


                String userId = user.getUserId();

                return ResponseEntity.ok().body(Map.of("success", true, "userId", userId));
            } else {
                return ResponseEntity.ok().body(Map.of("success", false));
            }

        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }

    //회원가입 전화번호인증
    @PostMapping("/insert-send-message")
    public ResponseEntity<?> send(@RequestParam String tel) {
        try {
            User user = userRepository.findByTel(tel);
            if (user != null) {
                return ResponseEntity.ok(Map.of("success", false));
            }
            // 인증번호를 생성합니다.
            String code = String.format("%06d", new Random().nextInt(1000000));

            // 메시지를 설정합니다.
            Message message = new Message();
            String pushtrip = "발신자"; 
            message.setFrom(pushtrip);
            message.setTo(tel);
            message.setText("pushtrip 인증번호: " + code);

            // 메시지를 전송합니다.
            SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println(response);

            // 인증번호를 저장합니다.
            verificationCodes.put(tel, code);

            // 성공 응답을 반환합니다.
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }


    @PostMapping("/insert-verify-code")
    public ResponseEntity<?> verifyCode(@RequestParam String code, @RequestParam String tel) {
        try {
            // 인증 코드와 전화번호를 검증하는 로직
            String storedCode = verificationCodes.get(tel); // 전화번호를 키로 사용하여 인증 코드를 가져옴

            // 디버깅 출력
            System.out.println(storedCode); // 저장된 인증 코드
            System.out.println(code); // 요청된 인증 코드



            if (storedCode != null && storedCode.equals(code)) {
                // 인증번호 확인 성공
                verificationCodes.remove(tel); // 성공 후 인증번호를 제거 (재사용 방지)
                return ResponseEntity.ok().body(Map.of("success", true));
            } else {
                // 인증번호 확인 실패
                return ResponseEntity.ok().body(Map.of("success", false));
            }
        }catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }

    //비밀번호찾기 전화번호 인증
    @PostMapping("/verify-phone-code-password")
    public ResponseEntity<?> verifyPasswordCode(@RequestParam String tel, @RequestParam String phoneCode) {
        try {
            User user = userRepository.findByTel(tel);
            System.out.println(user);
            if (user != null && user.getVerificationCode() != null
                    && user.getVerificationCode().equals(phoneCode)
                    && LocalDateTime.now().isBefore(user.getVerificationCodeExpiry())) {


                String tempPassword = generateTemporaryPassword();

                String encodedTemporaryPassword = bCryptPasswordEncoder.encode(tempPassword);
                user.setPassword(encodedTemporaryPassword);
                userRepository.save(user);

                Message message = new Message();
                String pushtrip = "발신자";
                message.setFrom(pushtrip);
                message.setTo(tel);
                message.setText("pushtrip임시비밀번호: " + tempPassword + "\n로그인 후 비밀번호를 변경해 주세요.");
                SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));


                return ResponseEntity.ok().body(Map.of("success", true));
            } else {
                return ResponseEntity.ok().body(Map.of("success", false));
            }
        }catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }


    private String generateTemporaryPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(TEMP_PASSWORD_LENGTH);
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }


}






