package com.golden_dobakhe.HakPle.domain.user.exception;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "사용자 관련 에러 코드")
public enum UserErrorCode {

    @Schema(description = "존재하지 않는 사용자")
    USER_NOT_FOUND("존재하지 않는 사용자"),

    @Schema(description = "현재 비밀번호가 일치하지 않음")
    WRONG_CURRENT_PASSWORD("현재 비밀번호가 일치하지 않음"),

    @Schema(description = "비밀번호 확인이 일치하지 않음")
    PASSWORD_CONFIRM_NOT_MATCH( "비밀번호 확인이 일치하지 않음"),

    @Schema(description = "해당 학원 코드를 찾지 못함")
    ACADEMY_ID_NOT_FOUND("해당 학원 코드를 찾지 못함"),

    @Schema(description = "아이디를 이미 사용 중입니다.")
    USERNAME_DUPLICATE("아이디를 이미 사용 중입니다."),

    @Schema(description = "닉네임을 이미 사용 중입니다.")
    NICKNAME_DUPLICATE("닉네임을 이미 사용 중입니다."),

    @Schema(description = "전화번호를 이미 사용 중입니다.")
    PHONENUM_DUPLICATE("전화번호를 이미 사용 중입니다.");

    private final String message;

    UserErrorCode(String message) {
        this.message = message;
    }
}