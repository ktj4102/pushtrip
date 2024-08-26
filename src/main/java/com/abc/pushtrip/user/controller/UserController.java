package com.abc.pushtrip.user.controller;

import com.abc.pushtrip.security.jwt.JWTUtil;
import com.abc.pushtrip.user.dto.CustomUserDetails;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import com.abc.pushtrip.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Controller
@SpringBootApplication
public class UserController {

    private final JWTUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    public UserController(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    // 회원가입 폼을 보여주는 메소드
    @GetMapping("/join")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        logger.info("join 페이지 진입");
        return "thymeleaf/join_membership";
    }

    @GetMapping("/main_travel_joy")
    public String main_travel_joy(Model model) {
        return "thymeleaf/main_travel_joy";
    }

    @GetMapping("/main_travel_food")
    public String main_travel_food(Model model) {
        return "thymeleaf/main_travel_food";
    }

    @GetMapping("/main_travel_hotel")
    public String main_travel_hotel(Model model) {
        return "thymeleaf/main_travel_hotel";
    }

    @GetMapping("/introduction")
    public String introduction(Model model) {
        return "thymeleaf/introduction";
    }

    @GetMapping("/loginForm")
    public String login(Model model) {
        return "thymeleaf/login";
    }

    @GetMapping("/mypage")
    public String test(Model model) {
        return "thymeleaf/mypage";
    }

    @GetMapping("/mypage_update")
    public String mypage_update(Model model) {
        return "thymeleaf/mypage_update";
    }


    @GetMapping("/callback")
    public String callback(Model model) {
        return "thymeleaf/callback";
    }

    @GetMapping("/findPassword")
    public String findPassword(Model model) {
        return "thymeleaf/passward_find";
    }

    @GetMapping("/findId")
    public String findId(Model model) {
        return "thymeleaf/id_find";
    }


    // 회원가입 폼 제출을 처리하는 메소드
    @PostMapping("/insert")
    public String createUser(@ModelAttribute("user") User user, BindingResult result, @RequestParam("picture") MultipartFile image) {
        try {
            // MultipartFile을 byte[]로 변환하여 user 객체의 picture 필드에 설정
            if (!image.isEmpty()) {
                user.setPicture(image.getBytes());
            }
        } catch (IOException e) {
            logger.error("이미지 변환 중 오류 발생", e);
            result.rejectValue("picture", "error.user", "Failed to upload picture");
            return "thymeleaf/join_membership";
        }
        // 유효성 검사 통과 후의 로직


        userService.createUser(user);
        try {
            userService.saveImage(user.getUserId(), image);
        } catch (IOException e) {
            // 이미지 저장 실패 처리
            logger.error("이미지 저장 중 오류 발생", e);
            result.rejectValue("picture", "error.user", "Failed to save picture");
            return "thymeleaf/join_membership";
        }

        return "thymeleaf/join_welcome";
    }

    @GetMapping("/user-info")
    public CustomUserDetails getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
            try {
                String userId = jwtUtil.getUserId(token);
                if (userId != null) {
                    User user = userRepository.findByUserId(userId); // Assumes userRepository has a method to find by userId
                    if (user != null) {
                        return new CustomUserDetails(user); // Return CustomUserDetails with user entity
                    }
                }
            } catch (Exception e) {
                // Handle token parsing exceptions or invalid tokens
                e.printStackTrace();
            }
        }
        return null; // Or return an appropriate response indicating an error
    }

    @PostMapping("/updateUser")
    public String updateUserP(@RequestParam("userId") String userId,
                              @ModelAttribute User user,
                              @RequestParam("new_picture") MultipartFile newPicture) throws IOException {
        logger.info("컨트롤러 진입 >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUser(userId, user, newPicture);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/updateUserN")
    public String updateUserP(@RequestParam("userId") String userId,
                              @ModelAttribute User user ) throws IOException {
        logger.info("컨트롤러 진입 >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUserN(userId, user);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/updateUserT")
    public String updateUserT(@RequestParam("userId") String userId,
                              @ModelAttribute User user ) throws IOException {
        logger.info("컨트롤러 진입 >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUserT(userId, user);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/updateUserE")
    public String updateUserE(@RequestParam("userId") String userId,
                              @ModelAttribute User user ) throws IOException {
        logger.info("컨트롤러 진입 >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUserE(userId, user);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/updateUserB")
    public String updateUserB(@RequestParam("userId") String userId,
                              @ModelAttribute User user ) throws IOException {
        logger.info("컨트롤러 진입 >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUserB(userId, user);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/updateUserA")
    public String updateUserA(@RequestParam("userId") String userId,
                              @ModelAttribute User user ) throws IOException {
        logger.info("컨트롤러 진입 >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUserA(userId, user);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/updateUserP")
    public String updateUserPa(@RequestParam("userId") String userId,
                               @ModelAttribute User user ) throws IOException {
        logger.info("psssword controller >>> : ");
        // 사용자 ID와 파일을 기반으로 프로필 사진 업데이트
        userService.updateUserP(userId, user);
        return "redirect:/mypage_update"; // 업데이트 후 리다이렉트할 페이지
    }

    @PostMapping("/deleteUser")
    public String deleteUser(User user) throws IOException {
        logger.info("deleteUser >> : ");
        userService.deleteUser(user);
        return "thymeleaf/Login";
    }

}