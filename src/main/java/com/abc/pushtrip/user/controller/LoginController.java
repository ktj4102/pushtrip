package com.abc.pushtrip.user.controller;

import com.abc.pushtrip.security.jwt.JWTUtil;
import com.abc.pushtrip.user.entity.SnsUser;
import com.abc.pushtrip.user.service.KakaoService;
import com.abc.pushtrip.user.service.NaverService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LoginController {

    @Autowired
    private final NaverService naverService;

    @Autowired
    private final KakaoService kakaoService;

    @Autowired
    private final JWTUtil jwtUtil;




    @GetMapping("/callback/naver")
    public ResponseEntity<Void> callbackNaver(
            @RequestParam String code,
            HttpSession session) throws Exception {

        System.out.println("callbackNaver 함수 진입 >>>>>>>>>>>");

        // Naver 서비스로부터 사용자 정보를 가져옴
        SnsUser naverUser = naverService.getNaverInfo(code);

        // 사용자 정보가 없거나 이메일이 없는 경우 오류 처리
        if (naverUser == null || naverUser.getEmail() == null) {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                    .body(null);
        }

        // JWT 액세스 토큰 발급 (15분 유효)
        String accessToken = jwtUtil.createJwt(naverUser.getEmail(), naverUser.getRole(), 15 * 60 * 1000L); // 15분
        // JWT 리프레시 토큰 발급 (1일 유효)
        String refreshToken = jwtUtil.createRefreshToken(naverUser.getEmail(), 60 * 60 * 1000L); // 1일

        // 세션에 토큰 저장
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);

        // 리다이렉트할 URL 설정
        String redirectUrl = "http://localhost:8082/callback";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(redirectUrl));  // 리다이렉트할 URL 설정

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(httpHeaders)
                .build();
    }

    @GetMapping("/callback/kakao")
    public ResponseEntity<Void> callbackKakao(
            @RequestParam String code,
            @RequestParam String state,
            HttpSession session) throws Exception {

        // code와 state를 출력해 확인
        System.out.println("Code: " + code);
        System.out.println("State: " + state);

        // Kakao 서비스로부터 사용자 정보를 가져옴
        SnsUser kakaoUser = kakaoService.getKakaoInfo(code);

        if (kakaoUser == null || kakaoUser.getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // JWT 액세스 토큰 및 리프레시 토큰 발급
        String accessToken = jwtUtil.createJwt(kakaoUser.getEmail(), kakaoUser.getRole(), 15 * 60 * 1000L);
        String refreshToken = jwtUtil.createRefreshToken(kakaoUser.getEmail(), 60 * 60 * 1000L);

        // 세션에 토큰 저장
        session.setAttribute("accessToken", accessToken);
        session.setAttribute("refreshToken", refreshToken);

        // 리다이렉트할 URL 설정
        String redirectUrl = "http://localhost:8082/callback";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(redirectUrl));  // 리다이렉트할 URL 설정

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(httpHeaders)
                .build();
    }

    @GetMapping("/getTokens")
    @ResponseBody
    public Map<String, String> getTokens(HttpSession session) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", (String) session.getAttribute("accessToken"));
        tokens.put("refreshToken", (String) session.getAttribute("refreshToken"));
        return tokens;
    }
}
