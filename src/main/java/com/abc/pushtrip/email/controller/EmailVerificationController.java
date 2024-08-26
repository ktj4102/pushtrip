package com.abc.pushtrip.email.controller;

import com.abc.pushtrip.email.service.EmailService;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import com.abc.pushtrip.user.service.UserService; // 사용자 서비스를 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@RestController
public class EmailVerificationController {


    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationController.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TEMP_PASSWORD_LENGTH = 8;

    private final EmailService emailService;
    private final UserService userService; // 사용자 서비스 추가

    public EmailVerificationController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/send-email-code")
    public ResponseEntity<?> sendEmailCode(@RequestParam String email, @RequestParam String userId) {
        try {
            User user = userRepository.findByEmail(email);
            logger.info("useId1"+user.getUserId());
            logger.info("useId2"+userId);
            if (Objects.equals(userId, user.getUserId())) {
                String code = String.format("%06d", new Random().nextInt(1000000));
                LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5); // 코드 만료 시간 설정

                // 인증 코드와 만료 시간을 사용자와 관련된 데이터베이스 필드에 저장
                user.setVerificationCode(code);
                user.setVerificationCodeExpiry(expiryDate);
                userRepository.save(user);

                // 이메일로 인증 코드 전송
                emailService.sendVerificationCode(email, code);

                return ResponseEntity.ok().body(Map.of("success", true));
            } else {
                return ResponseEntity.ok().body(Map.of("success", false));
            }
        }catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }


    @PostMapping("/verify-email-code")
    public ResponseEntity<?> verifyEmailCode(@RequestParam String email, @RequestParam String code) {
        try {
            // 인증 코드 검증 및 임시 비밀번호 발급 로직 추가

            User user = userRepository.findByEmail(email);

            if (user != null && user.getVerificationCode() != null
                    && user.getVerificationCode().equals(code)
                    && LocalDateTime.now().isBefore(user.getVerificationCodeExpiry())) {

                // 인증 코드가 유효할 경우 임시 비밀번호 설정
                String tempPassword = generateTemporaryPassword();

                String encodedTemporaryPassword = bCryptPasswordEncoder.encode(tempPassword);
                user.setPassword(encodedTemporaryPassword);
                userRepository.save(user);
                emailService.sendTemporaryPassword(email, tempPassword);
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
