package com.abc.pushtrip.Message.service;

import com.abc.pushtrip.Message.dto.MessageDto;
import com.abc.pushtrip.Message.entity.Message;
import com.abc.pushtrip.Message.repository.MessageRepository;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.entity.SnsUser;
import com.abc.pushtrip.user.repository.UserRepository;
import com.abc.pushtrip.user.repository.SnsUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SnsUserRepository snsUserRepository; // SNS 유저 레포지토리 추가

    @Transactional
    public MessageDto write(MessageDto messageDto) {
        // 받는 사람 조회
        User receiver = userRepository.findByUserId(messageDto.getReceiverUserId());
        SnsUser snsReceiver = null;

        if (receiver == null) {
            // SNS 사용자는 이메일로 처리
            snsReceiver = snsUserRepository.findByEmail(messageDto.getReceiverUserId());
            if (snsReceiver == null) {
                throw new IllegalArgumentException("받는 사람을 찾을 수 없습니다.");
            }
            messageDto.setReceiverUserId(snsReceiver.getEmail()); // SNS 사용자의 이메일을 받는 사람 ID로 설정
        } else {
            messageDto.setReceiverUserId(receiver.getUserId()); // 일반 사용자의 userId 설정
        }

        // 보낸 사람 조회
        User sender = userRepository.findByUserId(messageDto.getSenderUserId());
        SnsUser snsSender = null;

        if (sender == null) {
            // SNS 사용자는 이메일로 처리
            snsSender = snsUserRepository.findByEmail(messageDto.getSenderUserId());
            if (snsSender == null) {
                throw new IllegalArgumentException("보낸 사람을 찾을 수 없습니다.");
            }
            messageDto.setSenderUserId(snsSender.getEmail()); // SNS 사용자의 이메일을 보낸 사람 ID로 설정
        } else {
            messageDto.setSenderUserId(sender.getUserId()); // 일반 사용자의 userId 설정
        }

        // 메시지 저장
        Message message = new Message();
        message.setReceiverUserId(messageDto.getReceiverUserId()); // receiver에 일반 사용자 userId 또는 SNS 사용자 email 저장
        message.setSenderUserId(messageDto.getSenderUserId()); // sender에 일반 사용자 userId 또는 SNS 사용자 email 저장
        message.setTitle(messageDto.getTitle());
        message.setContent(messageDto.getContent());
        message.setDeletedByReceiver(false);
        message.setDeletedBySender(false);
        message.setInsertDate(LocalDateTime.now());
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }

    @Transactional
    public List<MessageDto> receivedMessage(String userId) {
        // 받은 편지함 불러오기 (일반 사용자 또는 SNS 사용자)
        List<Message> messages = messageRepository.findAllByReceiverUserId(userId);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (Message message : messages) {
            if (!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    @Transactional
    public Object deleteMessageByReceiver(int id, String userId) {
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        if (userId.equals(message.getReceiverUserId())) {
            message.deleteByReceiver(); // 받은 사람에게 메시지 삭제
            if (message.isDeleted()) {
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }

    @Transactional
    public List<MessageDto> sentMessage(String userId) {
        // 보낸 편지함 불러오기 (일반 사용자 또는 SNS 사용자)
        List<Message> messages = messageRepository.findAllBySenderUserId(userId);
        List<MessageDto> messageDtos = new ArrayList<>();

        for (Message message : messages) {
            if (!message.isDeletedBySender()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    @Transactional
    public Object deleteMessageBySender(int id, String userId) {
        Message message = messageRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        if (userId.equals(message.getSenderUserId())) {
            message.deleteBySender(); // 보낸 사람에게 메시지 삭제
            if (message.isDeleted()) {
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }
}
