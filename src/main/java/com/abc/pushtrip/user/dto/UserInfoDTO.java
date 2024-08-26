package com.abc.pushtrip.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {
    private String userId;
    private String password;
    private String email;
    private String role;
    private String name;
    private String tel;
    private String gender;
    private String birth;
    private String zoneCode;
    private String roadAddr;
    private String roadDetail;
    private String jibunAddr;
    private String picture; // base64 encoded image
    private String insertDate; // 가입일
    private String updateDate; // 수정일
}
