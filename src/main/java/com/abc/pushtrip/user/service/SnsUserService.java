package com.abc.pushtrip.user.service;

import com.abc.pushtrip.user.dto.KakaoDTO;
import com.abc.pushtrip.user.dto.NaverDTO;
import com.abc.pushtrip.user.entity.SnsUser;
import com.abc.pushtrip.user.repository.SnsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnsUserService {

    private final SnsUserRepository snsuserRepository;

    public SnsUser saveKakaoUser(KakaoDTO kakaoDTO) {
        SnsUser user = snsuserRepository.findByEmail(kakaoDTO.getEmail());

        if (user == null) {
            user = new SnsUser();
            user.setId(kakaoDTO.getId());
            user.setEmail(kakaoDTO.getEmail());
            user.setName(kakaoDTO.getUsername()); // 카카오에서 name이 없는 경우 nickname을 사용
            user.setUsername(kakaoDTO.getUsername());
            user.setProfileImage(kakaoDTO.getProfileImage());
            user.setProvider("kakao");
            user.setRole("USER"); // 기본 롤 설정
            // 필요한 경우, gender, birthday, birthYear, mobile 등을 추가
        }

        return snsuserRepository.save(user);
    }

    public SnsUser saveNaverUser(NaverDTO naverDTO) {
        SnsUser user = snsuserRepository.findByEmail(naverDTO.getEmail());

        if (user == null) {
            user = new SnsUser();
            user.setId(naverDTO.getId());
            user.setEmail(naverDTO.getEmail());
            user.setName(naverDTO.getName());
            user.setUsername(naverDTO.getUsername());
            user.setProfileImage(naverDTO.getProfileImage());
            user.setGender(naverDTO.getGender());
            user.setBirthday(naverDTO.getBirthday());
            user.setBirthYear(naverDTO.getBirthYear());
            user.setMobile(naverDTO.getMobile());
            user.setProvider("naver");
            user.setRole("USER"); // 기본 롤 설정
        }

        return snsuserRepository.save(user);
    }
}
