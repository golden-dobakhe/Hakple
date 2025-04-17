package com.golden_dobakhe.HakPle.domain.user.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "현재 비밀번호로 새 비밀번호를 변경하는 요청 DTO")
public class ChangePasswordRequest {

    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    @Schema(description = "현재 비밀번호", example = "oldPassword123!")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력하세요.")
    @Schema(description = "새 비밀번호", example = "newPassword456!")

    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인을 입력하세요.")
    @Schema(description = "새 비밀번호 확인", example = "newPassword456!")
    private String newPasswordConfirm;
}