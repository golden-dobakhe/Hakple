package com.golden_dobakhe.HakPle.domain.user.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원 탈퇴 요청 DTO")
public class DeleteUserRequestDto {
    @Schema(description = "현재 비밀번호", example = "mySecurePassword")
    private String password;
}
