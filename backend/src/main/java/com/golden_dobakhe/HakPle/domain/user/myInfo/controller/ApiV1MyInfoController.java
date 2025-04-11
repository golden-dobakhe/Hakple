package com.golden_dobakhe.HakPle.domain.user.myInfo.controller;

import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoResponseDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoUpdateRequestDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.service.MyInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "내 정보 조회",
            description = "사용자의 프로필 정보를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/myInfo")
//    public ResponseEntity<MyInfoRequestDto> getMyInfo(Authentication authentication) {
//        String username = authentication.getName(); // JWT 필터가 유저네임 넣어줌
    public ResponseEntity<MyInfoResponseDto> getMyInfo(@RequestParam("userName") String userName) {
        MyInfoResponseDto dto = myInfoService.getMyInfo(userName);
        return ResponseEntity.ok(dto);
    }


    @Operation(
            summary = "내 정보 수정",
            description = "사용자의 닉네임, 전화번호, 학원 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "입력 값 오류"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
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
