package com.golden_dobakhe.HakPle.domain.user.myInfo.controller;

import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoRequestDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/myInfos")
public class ApiV1MyInfoController {
    private final MyInfoService myInfoService;

    @GetMapping("/myInfo")
//    public ResponseEntity<MyInfoRequestDto> getMyInfo(Authentication authentication) {
//        String username = authentication.getName(); // JWT 필터가 유저네임 넣어줌
    public ResponseEntity<MyInfoRequestDto> getMyInfo(@RequestParam("userName") String userName) {
        MyInfoRequestDto dto = myInfoService.getMyInfo(userName);
        return ResponseEntity.ok(dto);
    }
}
