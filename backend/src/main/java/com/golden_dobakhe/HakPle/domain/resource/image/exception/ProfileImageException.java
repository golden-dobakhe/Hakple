package com.golden_dobakhe.HakPle.domain.resource.image.exception;

public class ProfileImageException extends RuntimeException {
    private final ImageErrorCode errorCode;

    public ProfileImageException(ImageErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ImageErrorCode getErrorCode() {
        return errorCode;
    }
}