package com.golden_dobakhe.HakPle.domain.user.user.controller;

import com.golden_dobakhe.HakPle.domain.user.user.dto.ChangePasswordRequest;
import com.golden_dobakhe.HakPle.domain.user.user.dto.ResetPasswordRequest;
import com.golden_dobakhe.HakPle.domain.user.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.user.service.UserService;
import com.golden_dobakhe.HakPle.security.AnotherCustomUserDetails;
import com.golden_dobakhe.HakPle.security.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "회원가입 API", description = "사용자 관련 API")
public class ApiV1UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "유저 정보를 입력하여 회원가입을 진행합니다.")
    @PostMapping("/userreg")
    public ResponseEntity<String> register(
            @Parameter(description = "회원가입 정보", required = true)
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    bindingResult.getFieldErrors().stream()
                            .map(error -> error.getField() + ": " + error.getDefaultMessage())
                            .collect(Collectors.joining(", "))
            );
        }

        userService.register(userDTO);
        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @Operation(summary = "비밀번호 변경", description = "로그인된 사용자가 현재 비밀번호를 확인하고 새 비밀번호로 변경합니다.")
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal AnotherCustomUserDetails principal,
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        userService.changePasswordWithOldPassword(
                principal.getUser().getId(),
                request.getCurrentPassword(),
                request.getNewPassword(),
                request.getNewPasswordConfirm()
        );
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @Operation(summary = "비밀번호 재설정", description = "비밀번호 찾기 이후 인증 완료된 사용자가 새 비밀번호를 설정합니다.")
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Parameter(description = "사용자 ID", example = "1")
            @AuthenticationPrincipal AnotherCustomUserDetails principal,
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        userService.resetPassword(
                principal.getUser().getId(),
                request.getNewPassword(),
                request.getNewPasswordConfirm()
        );
        return ResponseEntity.ok("비밀번호가 재설정되었습니다.");
    }


}
