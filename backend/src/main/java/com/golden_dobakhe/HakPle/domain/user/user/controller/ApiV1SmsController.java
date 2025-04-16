package com.golden_dobakhe.HakPle.domain.user.user.controller;

import com.golden_dobakhe.HakPle.domain.user.user.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sms")
@Tag(name = "SMS 인증 API", description = "휴대폰 문자 인증번호 전송 및 인증")
public class ApiV1SmsController {

    private final SmsService smsService;

    @Operation(summary = "인증번호 전송", description = "사용자의 휴대폰 번호로 인증번호를 전송합니다.")
    @PostMapping("/send")
    public ResponseEntity<?> sendCode(
            @Parameter(description = "휴대폰 번호", example = "01012345678")
            @RequestParam(name = "phone") String phone
    ) {
        smsService.sendVerificationCode(phone);
        return ResponseEntity.ok("인증번호 전송 완료");
    }

    @Operation(summary = "인증번호 검증", description = "입력한 인증번호가 올바른지 확인합니다.")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @Parameter(description = "휴대폰 번호", example = "01012345678")
            @RequestParam(name = "phone") String phone,

            @Parameter(description = "사용자가 입력한 인증번호", example = "123456")
            @RequestParam(name = "code") String code
    ) {
        boolean valid = smsService.verifyCode(phone, code);
        if (valid) return ResponseEntity.ok("인증 성공");
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
    }
}
