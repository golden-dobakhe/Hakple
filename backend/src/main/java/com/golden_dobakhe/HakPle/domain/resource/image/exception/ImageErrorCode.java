package com.golden_dobakhe.HakPle.domain.resource.image.exception;

public enum ImageErrorCode {
    USER_NOT_FOUND("유저를 찾을 수 없습니다."),
    FILE_EMPTY("업로드할 파일이 없습니다."),
    UPLOAD_FAIL("파일 업로드에 실패했습니다."),
    IMAGE_NOT_FOUND("사진을 찾을 수 없습니다.");

    private final String message;

    ImageErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
