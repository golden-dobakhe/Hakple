package com.golden_dobakhe.HakPle.domain.user.controller;

import com.golden_dobakhe.HakPle.domain.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class ApiV1UserController {
    private final UserService userService;

    @PostMapping("/userreg")
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시
            return ResponseEntity.badRequest().body(
                    bindingResult.getFieldErrors().stream()
                            .map(error -> error.getField() + ": " + error.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }

        // 아이디와 닉네임 중복 확인
        if (userService.usernameFail(userDTO.getUserName())) {
            return ResponseEntity.badRequest().body("아이디가 이미 사용 중입니다.");
        }

        if (userService.nicknameFail(userDTO.getNickName())) {
            return ResponseEntity.badRequest().body("닉네임이 이미 사용 중입니다.");
        }

        // 회원가입 처리
        userService.register(userDTO);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }
}
