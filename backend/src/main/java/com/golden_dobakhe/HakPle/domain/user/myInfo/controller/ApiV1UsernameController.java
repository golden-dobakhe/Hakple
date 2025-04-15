package com.golden_dobakhe.HakPle.domain.user.myInfo.controller;

import com.golden_dobakhe.HakPle.domain.user.myInfo.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usernames")
public class ApiV1UsernameController {
    private final UsernameService usernameService;

    //아이디 찾기
    @GetMapping("/find-username")
    public ResponseEntity<String> findUserName(
            @RequestParam(name = "nickName") String nickName,
            @RequestParam(name = "phoneNum") String phoneNum) {
        try {
            String userName = usernameService.findUserNameByPhoneNum(nickName, phoneNum);
            return ResponseEntity.ok("회원님의 아이디는: " + userName + " 입니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
