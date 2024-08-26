package com.abc.pushtrip.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SNS_USER")
@Getter
@Setter
public class SnsUser {

    @Id
    @Column(name = "ID", nullable = false)
    private String id; // 카카오나 네이버에서 제공하는 ID를 사용

    @Column(name = "EMAIL", nullable = true)
    private String email;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "USERNAME", nullable = true)
    private String username;

    @Column(name = "PROFILE_IMAGE", nullable = true)
    private String profileImage;

    @Column(name = "GENDER", nullable = true)
    private String gender;

    @Column(name = "BIRTHDAY", nullable = true)
    private String birthday;

    @Column(name = "BIRTH_YEAR", nullable = true)
    private String birthYear;

    @Column(name = "MOBILE", nullable = true)
    private String mobile;

    @Column(name = "PROVIDER", nullable = false)
    private String provider; // 'kakao' or 'naver'

    @Column(name = "ROLE", nullable = false)
    private String role = "USER";
}
