package com.abc.pushtrip.user.service;

import com.abc.pushtrip.security.config.SnsConfig;
import com.abc.pushtrip.user.dto.NaverDTO;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class NaverService {

    private final SnsConfig snsConfig;
    private final SnsUserService snsUserService;

    private static final String NAVER_AUTH_URI = "https://nid.naver.com";
    private static final String NAVER_API_URI = "https://openapi.naver.com";

    public String getNaverLogin() {
        String naverClientId = snsConfig.getNaverClientId();
        String naverRedirectUrl = snsConfig.getNaverRedirectUrl();

        return NAVER_AUTH_URI + "/oauth2.0/authorize"
                + "?client_id=" + naverClientId
                + "&redirect_uri=" + URLEncoder.encode(naverRedirectUrl, StandardCharsets.UTF_8)
                + "&response_type=code";
    }

    public SnsUser getNaverInfo(String code) throws Exception {
        if (code == null) throw new Exception("Failed to get authorization code");
        System.out.println("getNaverInfo 함수 진입 >>>");
        String accessToken;
        String refreshToken;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", snsConfig.getNaverClientId());
            params.add("client_secret", snsConfig.getNaverClientSecret());
            params.add("code", code);
            params.add("redirect_uri", snsConfig.getNaverRedirectUrl());

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    NAVER_AUTH_URI + "/oauth2.0/token",
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

        NaverDTO naverDTO = getUserInfoWithToken(accessToken);
        return snsUserService.saveNaverUser(naverDTO);
    }

    private NaverDTO getUserInfoWithToken(String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                NAVER_API_URI + "/v1/nid/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("response");

        String id = (String) account.get("id");
        String email = (String) account.get("email");
        String name = (String) account.get("name");
        String username = (String) account.get("username");
        String profileImage = (String) account.get("profile_image");
        String gender = (String) account.get("gender");
        String birthday = (String) account.get("birthday");
        String birthYear = (String) account.get("birthyear");
        String mobile = (String) account.get("mobile");

        return NaverDTO.builder()
                .id(id)
                .email(email)
                .name(name)
                .username(username)
                .profileImage(profileImage)
                .gender(gender)
                .birthday(birthday)
                .birthYear(birthYear)
                .mobile(mobile)
                .build();
    }
}
