package com.abc.pushtrip.user.controller;

import com.abc.pushtrip.security.jwt.JWTUtil;
import com.abc.pushtrip.user.dto.UserInfoDTO;
import com.abc.pushtrip.user.entity.User;
import com.abc.pushtrip.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyoageController {

    private static final Logger logger = LoggerFactory.getLogger(MyoageController.class);

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public MyoageController(JWTUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @GetMapping("/user-info")
    public ResponseEntity<UserInfoDTO> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
            try {
                String userId = jwtUtil.getUserId(token);
                if (userId != null) {
                    User user = userRepository.findByUserId(userId); // Assumes userRepository has a method to find by userId
                    if (user != null) {
                        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                                .userId(user.getUserId())
                                .password(user.getPassword())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .name(user.getName())
                                .tel(user.getTel())
                                .gender(user.getGender())
                                .birth(user.getBirth())
                                .zoneCode(user.getZoneCode())
                                .roadAddr(user.getRoadAddr())
                                .roadDetail(user.getRoadDetail())
                                .jibunAddr(user.getJibunAddr())
                                .picture(user.getPictureFileAsBase64()) // Base64 encoded image
                                .insertDate(user.getInsertDate().toString()) // Convert date to string
                                .updateDate(user.getUpdateDate().toString())
                                .build();

                        return new ResponseEntity<>(userInfoDTO, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
                    }
                }
            } catch (Exception e) {
                logger.error("Token parsing or user retrieval error", e);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Token not valid or missing
    }
}
