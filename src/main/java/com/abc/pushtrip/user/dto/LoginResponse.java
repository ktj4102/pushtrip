package com.abc.pushtrip.user.dto;

public class LoginResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;

    // 기본 생성자
    public LoginResponse() {}

    // 생성자, 게터 및 세터
    public LoginResponse(String userId, String accessToken, String refreshToken) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
