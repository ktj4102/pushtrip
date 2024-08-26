package com.abc.pushtrip.security.oauth2.service;

import com.abc.pushtrip.security.oauth2.CustomOAuth2User;
import com.abc.pushtrip.security.oauth2.dto.KakaoResponse;
import com.abc.pushtrip.security.oauth2.dto.NaverResponse;
import com.abc.pushtrip.security.oauth2.dto.OAuth2Response;
import com.abc.pushtrip.security.oauth2.dto.UserDTO;
import com.abc.pushtrip.user.entity.SnsUser;
import com.abc.pushtrip.user.repository.SnsUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final SnsUserRepository snsUserRepository;

    public CustomOAuth2UserService(SnsUserRepository snsUserRepository) {
        this.snsUserRepository = snsUserRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("OAuth2User: {}", oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = getOAuth2Response(oAuth2User, registrationId);

        return processOAuth2User(oAuth2Response);
    }

    private OAuth2Response getOAuth2Response(OAuth2User oAuth2User, String registrationId) {
        if ("naver".equals(registrationId)) {
            return new NaverResponse(oAuth2User.getAttributes());
        } else if ("kakao".equals(registrationId)) {
            return new KakaoResponse(oAuth2User.getAttributes());
        } else {
            log.error("Unsupported registrationId: {}", registrationId);
            throw new OAuth2AuthenticationException("Unsupported registrationId");
        }
    }

    private OAuth2User processOAuth2User(OAuth2Response oAuth2Response) {
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        SnsUser snsUser = snsUserRepository.findByUsername(username);

        if (snsUser == null) {
            log.debug("Creating new user with username: {}", username);
            snsUser = createNewUser(oAuth2Response, username);
        } else {
            log.debug("Updating existing user with username: {}", username);
            updateExistingUser(snsUser, oAuth2Response);
        }

        if (snsUser == null) {
            log.error("Failed to create or update user for username: {}", username);
            throw new IllegalStateException("Failed to create or update user");
        }

        return createCustomOAuth2User(snsUser);
    }

    private SnsUser createNewUser(OAuth2Response oAuth2Response, String username) {
        SnsUser snsUser = new SnsUser();
        snsUser.setUsername(username);
        snsUser.setEmail(oAuth2Response.getEmail());
        snsUser.setName(oAuth2Response.getName());
        snsUser.setRole("ROLE_USER");

        return snsUserRepository.save(snsUser);
    }

    private void updateExistingUser(SnsUser snsUser, OAuth2Response oAuth2Response) {
        snsUser.setEmail(oAuth2Response.getEmail());
        snsUser.setName(oAuth2Response.getName());

        snsUserRepository.save(snsUser);
    }

    private CustomOAuth2User createCustomOAuth2User(SnsUser snsUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(snsUser.getUsername());
        userDTO.setName(snsUser.getName());
        userDTO.setRole(snsUser.getRole());

        return new CustomOAuth2User(userDTO);
    }
}
