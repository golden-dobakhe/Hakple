package com.golden_dobakhe.HakPle.domain.user.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Schema(description = "사용자 관련 에러 코드")
public enum UserErrorCode {

    @Schema(description = "존재하지 않는 사용자")
    USER_NOT_FOUND("존재하지 않는 사용자", HttpStatus.NOT_FOUND),

    @Schema(description = "현재 비밀번호가 일치하지 않음")
    WRONG_CURRENT_PASSWORD("현재 비밀번호가 일치하지 않음", HttpStatus.UNAUTHORIZED),

    @Schema(description = "비밀번호 확인이 일치하지 않음")
    PASSWORD_CONFIRM_NOT_MATCH("비밀번호 확인이 일치하지 않음", HttpStatus.BAD_REQUEST),

    @Schema(description = "해당 학원 코드를 찾지 못함")
    ACADEMY_ID_NOT_FOUND("해당 학원 코드를 찾지 못함", HttpStatus.NOT_FOUND),

    @Schema(description = "아이디를 이미 사용 중입니다.")
    USERNAME_DUPLICATE("아이디를 이미 사용 중입니다.", HttpStatus.CONFLICT),

    @Schema(description = "닉네임을 이미 사용 중입니다.")
    NICKNAME_DUPLICATE("닉네임을 이미 사용 중입니다.", HttpStatus.CONFLICT),


    @Schema(description = "전화번호를 이미 사용 중입니다.")
    PHONENUM_DUPLICATE("전화번호를 이미 사용 중입니다.",HttpStatus.CONFLICT),

    @Schema(description = "Access Token 블랙리스트 등록 실패")
    ACCESS_TOKEN_BLACKLIST_FAIL("Access Token 블랙리스트 등록 실패", HttpStatus.INTERNAL_SERVER_ERROR),

    @Schema(description = "Refresh 토큰 삭제 실패")
    REFRESH_TOKEN_DELETE_FAIL("Refresh 토큰 삭제 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR),

    @Schema(description = "ADMIN 접근 권한이 없습니다")
    FORBIDDEN("접근 권한이 없습니다.",HttpStatus.UNAUTHORIZED),

    @Schema(description = "인증되지 않은 사용자")
    UNAUTHORIZED("로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);


    private final String message;
    private final HttpStatus status;

    UserErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
