package com.abc.pushtrip.user.controller;

import com.abc.pushtrip.user.service.KakaoService;
import com.abc.pushtrip.user.service.NaverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginViewController {

    @Autowired
    private NaverService naverService;

    @Autowired
    private KakaoService kakaoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String snsLogin(Model model) {
        System.out.println("Naver URL 진입 >>>  ");
        String naverUrl = naverService.getNaverLogin();
        String kakaoUrl = kakaoService.getKakaoLogin();

        // 로그 추가
        System.out.println("Naver URL: " + naverUrl);
        System.out.println("Kakao URL: " + kakaoUrl);

        model.addAttribute("naverUrl", naverUrl);
        model.addAttribute("kakaoUrl", kakaoUrl);

        return "/loginForm"; // 경로 앞에 "/"를 제거
    }
}
