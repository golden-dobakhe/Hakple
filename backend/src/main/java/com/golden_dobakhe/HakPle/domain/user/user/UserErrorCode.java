package com.golden_dobakhe.HakPle.domain.user.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 관련 에러 코드")
public enum UserErrorCode {

    @Schema(description = "존재하지 않는 사용자")
    USER_NOT_FOUND,

    @Schema(description = "현재 비밀번호가 일치하지 않음")
    WRONG_CURRENT_PASSWORD,

    @Schema(description = "비밀번호 확인이 일치하지 않음")
    PASSWORD_CONFIRM_NOT_MATCH
}