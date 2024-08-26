package com.abc.pushtrip.email.service;

import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void sendVerificationCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("pushtrip 인증번호");
        message.setText("인증번호: " + code);

        // Send the email
        emailSender.send(message);

    }

    public void sendTemporaryPassword(String email, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("pushtrip 임시 비밀번호");
        message.setText("안녕하세요,\n\n임시 비밀번호는 다음과 같습니다: " + tempPassword + "\n\n로그인 후 비밀번호를 변경해 주세요.");
        emailSender.send(message);
    }


}
