package com.abc.pushtrip.security.controller;

import com.abc.pushtrip.security.jwt.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TokenController {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        // 쿠키에서 리프레시 토큰 가져오기
        String refreshToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.status(401).body("리프레시 토큰이 없습니다.");
        }

        try {
            Claims claims = jwtUtil.parseJwt(refreshToken);
            String userId = claims.get("userId", String.class);
            String newAccessToken = jwtUtil.createJwt(userId, claims.get("role").toString(), 60 * 60 * 1000L);

            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);

            return ResponseEntity.ok(response);
        } catch (JwtException e) {
            return ResponseEntity.status(401).body("유효하지 않은 리프레시 토큰");
        }
    }

    @PostMapping("/decode-token")
    public ResponseEntity<Map<String, String>> decodeToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        // 로그 추가: 요청 헤더가 제대로 전달되는지 확인
        System.out.println("Authorization Header: " + authorizationHeader);

        String token = authorizationHeader.replace("Bearer ", "");

        try {
            Claims claims = jwtUtil.parseJwt(token);
            String userId = claims.get("userId", String.class);
            String role = claims.get("role", String.class);

            Map<String, String> response = new HashMap<>();
            response.put("userId", userId);
            response.put("role", role);

            System.out.println("Decoded userId: " + userId);
            System.out.println("Decoded role: " + role);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}