package com.abc.pushtrip.Message.dto;

import com.abc.pushtrip.Message.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String title;
    private String content;
    private String senderUserId;  // User ID 또는 Email
    private String receiverUserId;  // User ID 또는 Email
    private String insertDate;

    // toDto 메서드에서 날짜 포맷팅 추가
    public static MessageDto toDto(Message message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");  // 원하는 날짜 포맷

        return new MessageDto(
                message.getId(),
                message.getTitle(),
                message.getContent(),
                message.getSenderUserId(),  // String 값으로 저장된 senderUserId 사용
                message.getReceiverUserId(),  // String 값으로 저장된 receiverUserId 사용
                message.getInsertDate().format(formatter)  // 날짜를 포맷팅하여 문자열로 변환
        );
    }
}
