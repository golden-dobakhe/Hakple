package com.golden_dobakhe.HakPle.domain.user.myInfo.exception;

public class MyInfoException extends RuntimeException {
    private final MyInfoErrorCode errorCode;

    public MyInfoException(MyInfoErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public MyInfoErrorCode getErrorCode() {
        return errorCode;
    }
}