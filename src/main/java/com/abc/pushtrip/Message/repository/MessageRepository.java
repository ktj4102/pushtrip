package com.abc.pushtrip.Message.repository;

import com.abc.pushtrip.Message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    // User ID 또는 Email로 받은 메시지를 조회
    List<Message> findAllByReceiverUserId(String receiverUserId);

    // User ID 또는 Email로 보낸 메시지를 조회
    List<Message> findAllBySenderUserId(String senderUserId);

}
