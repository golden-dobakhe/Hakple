package com.golden_dobakhe.HakPle.domain.user.controller;

import com.golden_dobakhe.HakPle.domain.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ApiV1UserController {
    private final UserService userService;


    //회원가입폼 요청
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/userreg")
    public String register(UserDTO userDTO) {
        userService.register(userDTO); // 서비스 호출

        // 회원가입 후 리다이렉트할 페이지 (예: 로그인 페이지나 완료 페이지)
        return "redirect:/api/v1/users/signup-success";
    }

}
