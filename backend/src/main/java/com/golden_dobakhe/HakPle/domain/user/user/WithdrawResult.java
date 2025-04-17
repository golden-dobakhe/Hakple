package com.golden_dobakhe.HakPle.domain.user.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 탈퇴 결과")
public enum WithdrawResult {

    @Schema(description = "회원 탈퇴 성공")
    SUCCESS,

    @Schema(description = "사용자가 존재하지 않음")
    USER_NOT_FOUND,

    @Schema(description = "비밀번호 불일치")
    WRONG_PASSWORD
}
