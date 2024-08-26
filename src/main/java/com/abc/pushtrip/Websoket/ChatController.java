package com.abc.pushtrip.Websoket;

import com.abc.pushtrip.questionforum.entity.QuestionForum;
import com.abc.pushtrip.questionforum.service.QuestionForumService;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/chattingRoom")
    public String chattingRoom(@RequestParam String userId, Model model) {
        Optional<User> user = userRepository.findById(userId);

        model.addAttribute("user", user);

        return "thymeleaf/chattingRoom";
    }




}