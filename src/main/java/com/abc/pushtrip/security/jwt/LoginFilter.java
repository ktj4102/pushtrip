package com.abc.pushtrip.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.abc.pushtrip.user.dto.CustomUserDetails;
import com.abc.pushtrip.user.dto.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 데이터를 LoginRequest 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

            String userId = loginRequest.getUserId();
            String password = loginRequest.getPassword();

            // 로그를 추가하여 userId와 password 값을 확인
            System.out.println("Attempting authentication for user: " + userId);

            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, password);
            System.out.println(authToken);
            // 인증 시도 및 결과 반환
            Authentication authResult = authenticationManager.authenticate(authToken);
            System.out.println(authResult);

            // 인증 성공 로그
            System.out.println("Authentication successful for user: " + userId);

            return authResult;
        } catch (AuthenticationException e) {
            // 인증 실패 시 예외 메시지 로그
            System.err.println("Authentication failed: " + e.getMessage());

            // AuthenticationException을 다시 던져서 상위 호출자에게 알림
            throw e;
        } catch (IOException e) {
            // JSON 변환 중 발생한 예외 처리
            System.err.println("IOException occurred: " + e.getMessage());
            throw new RuntimeException("Failed to parse login request", e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = customUserDetails.getUsername();

        String accessToken = jwtUtil.createJwt(userId, customUserDetails.getAuthorities().iterator().next().getAuthority(), 15 * 60 * 1000L); // 15분
        String refreshToken = jwtUtil.createRefreshToken(userId, 60 * 60 * 1000L); // 7일

        // 사용자 아이디를 쿠키로 설정
        Cookie userIdCookie = new Cookie("userId", userId);
        userIdCookie.setHttpOnly(true); // JavaScript에서 쿠키 접근 불가
        userIdCookie.setSecure(true); // HTTPS를 사용할 경우에만 설정
        userIdCookie.setPath("/");
        userIdCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효 기간을 7일로 설정
        System.out.println(userIdCookie);
        // 쿠키를 응답에 추가
        response.addCookie(userIdCookie);

        // 응답 헤더에 JWT 액세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + accessToken);

        // LoginResponse DTO에 정보 설정
        LoginRequest loginRequset = new LoginRequest();
        loginRequset.setUserId(userId);
        loginRequset.setAccessToken(accessToken);
        loginRequset.setRefreshToken(refreshToken);

        System.out.println("accessToken" + accessToken);
        System.out.println("refreshToken" + refreshToken);
        // JSON 응답으로 전송
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getWriter(), loginRequset);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("유효하지 않은 자격 증명");
    }
}
