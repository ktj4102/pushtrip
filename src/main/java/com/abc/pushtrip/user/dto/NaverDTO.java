package com.abc.pushtrip.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverDTO {
    private String id;
    private String email;
    private String name;
    private String username;
    private String profileImage;
    private String gender;
    private String birthday;
    private String birthYear;
    private String mobile;
}
