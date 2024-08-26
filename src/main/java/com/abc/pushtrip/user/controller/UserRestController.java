package com.abc.pushtrip.user.controller;

import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import com.abc.pushtrip.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;


    @Autowired
    public UserRestController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @PostMapping("/checkId")
    public ResponseEntity<?> checkId(@RequestParam String userId) {
        try {
        System.out.println("Checking userId: " + userId); // 로그 추가

        Optional<User> user = userRepository.findById(userId);
        System.out.println("ID user: " + user); // 로그 추가

        if (user.isPresent()) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.ok(Map.of("success", false));
        }
    }catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }


    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        try {
        System.out.println("Checking email: " + email); // 로그 추가

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (user.isPresent()) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            return ResponseEntity.ok(Map.of("success", false));
        }
    }catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return ResponseEntity.badRequest().body(Map.of("success", false));
        }
    }


}




