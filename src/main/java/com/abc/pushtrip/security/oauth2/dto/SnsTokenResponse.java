package com.abc.pushtrip.security.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자
public class SnsTokenResponse {
    private String status;
    private String message;
    private String accessToken;
    private String refreshToken;
}
