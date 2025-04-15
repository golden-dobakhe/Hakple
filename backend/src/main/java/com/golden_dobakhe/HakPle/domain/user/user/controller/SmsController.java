package com.golden_dobakhe.HakPle.domain.user.user.controller;

import com.golden_dobakhe.HakPle.domain.user.user.service.SmsService;
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
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<?> sendCode(@RequestParam(name = "phone") String phone) {
        smsService.sendVerificationCode(phone);
        return ResponseEntity.ok("인증번호 전송 완료");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "code") String code
    ) {
        boolean valid = smsService.verifyCode(phone, code);
        if (valid) return ResponseEntity.ok("인증 성공");
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
    }
}
