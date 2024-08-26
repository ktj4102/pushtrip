package com.abc.pushtrip.security.config;

import com.abc.pushtrip.security.jwt.JWTFilter;
import com.abc.pushtrip.security.jwt.JWTUtil;
import com.abc.pushtrip.security.jwt.LoginFilter;
import com.abc.pushtrip.security.oauth2.handler.CustomAuthenticationSuccessHandler;
import com.abc.pushtrip.security.oauth2.handler.CustomSuccessHandler;
import com.abc.pushtrip.security.oauth2.service.CustomOAuth2UserService;
import com.abc.pushtrip.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // 이 클래스가 스프링 설정 클래스임을 나타냅니다.
@EnableWebSecurity // 스프링 시큐리티를 활성화합니다.
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;

    //생성자
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil,
                          CustomOAuth2UserService customOAuth2UserService,
                          CustomSuccessHandler customSuccessHandler) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
    }

    // 비밀번호 인코딩을 위한 BCryptPasswordEncoder 빈을 등록합니다.
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈을 생성합니다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호를 비활성화합니다.
        http.csrf(AbstractHttpConfigurer::disable);

        // 폼 로그인을 비활성화합니다.
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP Basic 인증을 비활성화합니다.
        http.httpBasic(AbstractHttpConfigurer::disable);

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler));


        // 특정 URL 패턴에 대한 접근 권한을 설정합니다.
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll()
                .requestMatchers("/resources/**").permitAll()
                .anyRequest().authenticated());

        // LoginFilter를 UsernamePasswordAuthenticationFilter 위치에 추가
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                UsernamePasswordAuthenticationFilter.class);

        // JWTFilter를 LoginFilter보다 앞에 추가
        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // 세션 관리 정책을 무상태로 설정합니다.
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}