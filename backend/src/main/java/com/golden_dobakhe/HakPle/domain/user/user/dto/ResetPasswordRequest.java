package com.golden_dobakhe.HakPle.domain.user.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 재설정 요청 DTO")
public class ResetPasswordRequest {

    @NotBlank(message = "새 비밀번호를 입력하세요.")
    @Schema(description = "새 비밀번호", example = "resetPassword123!")
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인을 입력하세요.")
    @Schema(description = "새 비밀번호 확인", example = "resetPassword123!")
    private String newPasswordConfirm;
}

