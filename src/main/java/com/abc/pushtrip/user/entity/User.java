package com.abc.pushtrip.user.entity;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;


@Component
@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {


    @Id
    @Column(name = "USERID", nullable = false, length = 50)
    private String userId;

    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;


    @Column(name = "VERIFICATION_CODE", length = 200)
    private String verificationCode; // 인증 코드

    @Column(name = "VERIFICATION_CODE_EXPIRY")
    private LocalDateTime verificationCodeExpiry; // 인증 코드 만료 시간

    @Builder.Default
    @Column(name = "ROLE", nullable = false, length = 20)
    private String role = "N";

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "TEL", nullable = false, length = 16)
    private String tel;

    @Column(name = "GENDER", length = 10)
    private String gender;

    @Column(name = "BIRTH", length = 10)
    private String birth;

    @Column(name = "ZONECODE", length = 6)
    private String zoneCode;

    @Column(name = "ROADADDR", length = 500)
    private String roadAddr;

    @Column(name = "ROADDETAIL", length = 500)
    private String roadDetail;

    @Column(name = "JIBUNADDR", length = 500)
    private String jibunAddr;

    @Lob
    @Column(name = "PICTURE")
    private byte[] picture;

    @Builder.Default
    @Column(name = "DELETEYN", nullable = false, length = 1)
    private String deleteYn = "N";

    @Temporal(TemporalType.DATE)
    @Builder.Default
    @Column(name = "INSERTDATE", nullable = false)
    private Date insertDate = new Date();

    @Temporal(TemporalType.DATE)
    @Builder.Default
    @Column(name = "UPDATEDATE", nullable = false)
    private Date updateDate = new Date();


    public String getPictureFileAsBase64() {
        if (picture != null && picture.length > 0) {
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(picture);
        }
        return null;
    }


}

