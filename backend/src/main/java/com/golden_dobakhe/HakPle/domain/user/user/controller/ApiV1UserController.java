package com.golden_dobakhe.HakPle.domain.user.user.controller;

import com.golden_dobakhe.HakPle.domain.user.user.WithdrawResult;
import com.golden_dobakhe.HakPle.domain.user.user.dto.ChangePasswordRequest;
import com.golden_dobakhe.HakPle.domain.user.user.dto.DeleteUserRequestDto;
import com.golden_dobakhe.HakPle.domain.user.user.dto.ResetPasswordRequest;
import com.golden_dobakhe.HakPle.domain.user.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.user.service.UserService;
import com.golden_dobakhe.HakPle.security.AnotherCustomUserDetails;
import com.golden_dobakhe.HakPle.security.jwt.JwtTokenizer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
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
    private final JwtTokenizer jwtTokenizer;
    private final RedisTemplate<String, String> redisTemplate;

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

    @DeleteMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "비밀번호 확인 후 회원 비활성화 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "비밀번호 불일치"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    public ResponseEntity<String> withdrawUser(@RequestBody DeleteUserRequestDto dto,
                                               @AuthenticationPrincipal AnotherCustomUserDetails principal) {
        Long userId = principal.getUser().getId();
        WithdrawResult result = userService.withdraw(userId, dto.getPassword());

        return switch (result) {
            case SUCCESS -> ResponseEntity.ok("회원 탈퇴 완료");
            case USER_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 사용자입니다");
            case WRONG_PASSWORD -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다");
        };
    }


    @Operation(summary = "로그아웃", description = "Access Token을 블랙리스트에 등록하고 Refresh Token을 제거합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal AnotherCustomUserDetails principal,
                                         @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.replace("Bearer ", "");
        Long userId = principal.getUser().getId();

        userService.logout(token, userId);

        return ResponseEntity.ok("로그아웃 완료");
    }





}
