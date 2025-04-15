package com.golden_dobakhe.HakPle.domain.user.exception;

import lombok.Getter;

@Getter
public enum UserErrorCode {
    USERNAME_NOT_FOUND("해당 사용자 아이디를 찾지 못함"),
    ACADEMYID_NOT_FOUND("해당 학원 코드를 찾지 못함"),
    USERNAME_DUPLICATE("아이디를 이미 사용 중입니다."),
    NICKNAME_DUPLICATE("닉네임을 이미 사용 중입니다.");


    private final String message;

    UserErrorCode(String message) {
        this.message = message;
    }
}
