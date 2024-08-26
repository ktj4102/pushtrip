package com.abc.pushtrip.user.service;

import com.abc.pushtrip.security.config.SnsConfig;
import com.abc.pushtrip.user.dto.KakaoDTO;
import com.abc.pushtrip.user.entity.SnsUser;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final SnsConfig snsConfig;
    private final SnsUserService snsUserService;

    private static final String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private static final String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        String kakaoClientId = snsConfig.getKakaoClientId();
        String kakaoRedirectUrl = snsConfig.getKakaoRedirectUrl();

        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + kakaoClientId
                + "&redirect_uri=" + kakaoRedirectUrl
                + "&response_type=code";
    }

    public SnsUser getKakaoInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed to get authorization code");
        System.out.println("getKakaoInfo 함수 진입 >>>");
        String accessToken;
        String refreshToken;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", snsConfig.getKakaoClientId());
            params.add("client_secret", snsConfig.getKakaoClientSecret());
            params.add("code", code);
            params.add("redirect_uri", snsConfig.getKakaoRedirectUrl());

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");

            System.out.println(accessToken);
            System.out.println(refreshToken);

        } catch (Exception e) {
            throw new Exception("API call failed", e);
        }

        KakaoDTO kakaoDTO = getUserInfoWithToken(accessToken);
        return snsUserService.saveKakaoUser(kakaoDTO);
    }

    private KakaoDTO getUserInfoWithToken(String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject kakaoAccount = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) kakaoAccount.get("profile");

        String id = String.valueOf(jsonObj.get("id"));
        String email = (String) kakaoAccount.get("email");
        String profileImage = (String) profile.get("profile_image_url");
        String username = (String) profile.get("username");

        return KakaoDTO.builder()
                .id(id)
                .email(email)
                .profileImage(profileImage)
                .username(username)
                .build();
    }
}
