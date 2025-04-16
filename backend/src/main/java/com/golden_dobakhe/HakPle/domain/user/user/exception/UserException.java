package com.golden_dobakhe.HakPle.domain.user.user.exception;


import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;

public class UserException extends RuntimeException {
    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }

    public UserErrorCode getErrorCode() {
        return errorCode;
    }
}
