package com.abc.pushtrip.Message.controller;

import com.abc.pushtrip.Message.dto.MessageDto;
import com.abc.pushtrip.Message.service.MessageService;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.entity.SnsUser;
import com.abc.pushtrip.user.repository.UserRepository;
import com.abc.pushtrip.user.repository.SnsUserRepository;
import com.abc.pushtrip.security.jwt.JWTUtil;
import com.abc.pushtrip.Message.dto.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;
    private final SnsUserRepository snsUserRepository;
    private final JWTUtil jwtUtil;

    @ApiOperation(value = "쪽지 보내기", notes = "쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestHeader("Authorization") String token, @RequestBody MessageDto messageDto) {
        try {
            // JWT 토큰에서 유저 ID 추출 (보낸 사람)
            String senderUserId = jwtUtil.getUserId(token.replace("Bearer ", ""));
            System.out.println("컨트롤러 진입>>>>>>>>>>>>>>>" + token);
            System.out.println("컨트롤러 진입 >>>>>>>" +messageDto.getReceiverUserId() );
            System.out.println("컨트롤러 진입>>>>>>>>>>>>>>>" + senderUserId);

            // 보낸 사람 조회 (일반 사용자 조회)
            User sender = userRepository.findByUserId(senderUserId);
            if (sender == null) {
                // SNS 사용자 조회
                SnsUser snsSender = snsUserRepository.findByEmail(senderUserId);
                if (snsSender == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse<>("실패", "보낸 사람을 찾을 수 없습니다.", null));
                }
                senderUserId = snsSender.getEmail(); // SNS 사용자 ID 사용
            }

            // 받는 사람 조회 (일반 사용자 우선 조회)
            User receiver = userRepository.findByUserId(messageDto.getReceiverUserId());

            System.out.println("receiver receiver>>>>>>>>>>>>>>>" + receiver);
            SnsUser snsReceiver = null;
            if (receiver == null) {
                // SNS 사용자를 이메일로 조회
                snsReceiver = snsUserRepository.findByEmail(messageDto.getReceiverUserId());
                if (snsReceiver == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ApiResponse<>("실패", "받는 사람을 찾을 수 없습니다.", null));
                }
            }

            // 메시지 작성
            messageDto.setSenderUserId(senderUserId);

            if (receiver != null) {
                // 일반 사용자에게 메시지 전송
                messageDto.setReceiverUserId(receiver.getUserId());
            } else if (snsReceiver != null) {
                // SNS 사용자에게 메시지 전송 (이메일로 조회된 SNS 사용자)
                messageDto.setReceiverUserId(snsReceiver.getEmail());
            }

            MessageDto savedMessage = messageService.write(messageDto);


            // 성공 응답 반환
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>("성공", "쪽지를 보냈습니다.", savedMessage));

        } catch (IllegalArgumentException e) {
            // JWT 파싱 실패 등의 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("실패", "잘못된 요청입니다. JWT가 유효하지 않거나 형식이 잘못되었습니다.", null));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("실패", "서버에서 오류가 발생했습니다.", null));
        }
    }

    @ApiOperation(value = "받은 편지함 읽기", notes = "받은 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/received")
    public ResponseEntity<?> getReceivedMessage(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.getUserId(token.replace("Bearer ", ""));

        // 사용자 조회 (일반 사용자 우선 조회)
        User user = userRepository.findByUserId(userId);
        SnsUser snsUser = null;

        if (user == null) {
            // SNS 사용자 조회
            snsUser = snsUserRepository.findByEmail(userId); // 이메일로 SNS 사용자 조회
            if (snsUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("실패", "사용자를 찾을 수 없습니다.", null));
            }
        }

        // 받은 메시지 목록 가져오기
        List<MessageDto> messages = messageService.receivedMessage(
                user != null ? user.getUserId() : snsUser.getEmail()  // user가 null이 아니면 userId 사용, null이면 snsUser 이메일 사용
        );

        // 성공 응답 반환
        return ResponseEntity.ok(new ApiResponse<>("성공", "받은 쪽지를 불러왔습니다.", messages));
    }


    @ApiOperation(value = "받은 쪽지 삭제하기", notes = "받은 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/received/{id}")
    public ResponseEntity<?> deleteReceivedMessage(@RequestHeader("Authorization") String token,
                                                   @PathVariable("id") Integer id) {
        String userId = jwtUtil.getUserId(token.replace("Bearer ", ""));

        // 사용자 조회 (일반 사용자 우선 조회)
        User user = userRepository.findByUserId(userId);
        SnsUser snsUser = null;

        if (user == null) {
            // SNS 사용자 조회
            snsUser = snsUserRepository.findByEmail(userId);
            if (snsUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("실패", "사용자를 찾을 수 없습니다.", null));
            }
        }

        // 받은 쪽지 삭제
        String receiverId = user != null ? user.getUserId() : snsUser.getEmail();
        return ResponseEntity.ok(new ApiResponse<>("삭제 성공", id + "번 받은 쪽지를 삭제했습니다.",
                messageService.deleteMessageByReceiver(id, receiverId)));
    }

    @ApiOperation(value = "보낸 편지함 읽기", notes = "보낸 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sent")
    public ResponseEntity<?> getSentMessage(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.getUserId(token.replace("Bearer ", ""));

        // 사용자 조회 (일반 사용자 우선 조회)
        User user = userRepository.findByUserId(userId);
        SnsUser snsUser = null;

        if (user == null) {
            // SNS 사용자 조회
            snsUser = snsUserRepository.findByEmail(userId);
            if (snsUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("실패", "사용자를 찾을 수 없습니다.", null));
            }
        }

        // 보낸 메시지 목록 가져오기
        List<MessageDto> messages = messageService.sentMessage(user != null ? user.getUserId() : snsUser.getEmail());

        // 성공 응답 반환
        return ResponseEntity.ok(new ApiResponse<>("성공", "보낸 쪽지를 불러왔습니다.", messages));
    }

    @ApiOperation(value = "보낸 쪽지 삭제하기", notes = "보낸 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/sent/{id}")
    public ResponseEntity<?> deleteSentMessage(@RequestHeader("Authorization") String token, @PathVariable("id") Integer id) {
        String userId = jwtUtil.getUserId(token.replace("Bearer ", ""));

        // 사용자 조회 (일반 사용자 우선 조회)
        User user = userRepository.findByUserId(userId);
        SnsUser snsUser = null;

        if (user == null) {
            // SNS 사용자 조회
            snsUser = snsUserRepository.findByEmail(userId);
            if (snsUser == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("실패", "사용자를 찾을 수 없습니다.", null));
            }
        }

        // 보낸 쪽지 삭제
        String senderId = user != null ? user.getUserId() : snsUser.getEmail();
        return ResponseEntity.ok(new ApiResponse<>("삭제 성공", id + "번 보낸 쪽지를 삭제했습니다.",
                messageService.deleteMessageBySender(id, senderId)));
    }
}
