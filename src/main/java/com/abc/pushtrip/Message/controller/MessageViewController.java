package com.abc.pushtrip.Message.controller;

import com.abc.pushtrip.travelforum.entity.TravelForum;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class MessageViewController {

    @GetMapping("messageForm")
    public String messageForm(Model model) {
        return "thymeleaf/messageForm";
    }
}
