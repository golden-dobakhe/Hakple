package com.golden_dobakhe.HakPle.domain.user.myInfo.controller;

import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoResponseDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoUpdateRequestDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.service.MyInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<MyInfoResponseDto> getMyInfo(@RequestParam("userName") String userName) {
        MyInfoResponseDto dto = myInfoService.getMyInfo(userName);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/myinfo/update")
//    public ResponseEntity<String> updateMyInfo(
//            @RequestBody MyInfoUpdateRequestDto requestDto, Authentication authentication
//    ) {
//        String username = authentication.getName(); // JWT 필터에서 유저네임 꺼내옴
    public ResponseEntity<String> updateMyInfo(
            @RequestParam("userName") String userName,
            @RequestBody MyInfoUpdateRequestDto myInfoUpdateRequestDto) {

        myInfoService.updateMyInfo(userName, myInfoUpdateRequestDto);
        return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
    }
}
