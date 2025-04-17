package com.golden_dobakhe.HakPle.domain.resource.image.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ImageErrorCode {
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FILE_EMPTY("업로드할 파일이 없습니다.", HttpStatus.BAD_REQUEST),
    UPLOAD_FAIL("파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_NOT_FOUND("사진을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;

    ImageErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}