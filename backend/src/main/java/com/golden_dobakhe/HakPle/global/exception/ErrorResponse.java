package com.golden_dobakhe.HakPle.global.exception;

import com.golden_dobakhe.HakPle.domain.resource.image.exception.ImageErrorCode;
import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoErrorCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public ErrorResponse(HttpStatus status, String message) {
        this(status.value(), status.getReasonPhrase(), message, LocalDateTime.now());
    }

    public ErrorResponse(UserErrorCode errorCode, String message) {
        this(errorCode.getStatus().value(),
                errorCode.getStatus().getReasonPhrase(),
                errorCode.getMessage(),
                LocalDateTime.now());
    }
    public ErrorResponse(MyInfoErrorCode errorCode) {
        this(
                errorCode.getStatus().value(),
                errorCode.getStatus().getReasonPhrase(),
                errorCode.getMessage(),
                LocalDateTime.now()
        );
    }

    public ErrorResponse( ImageErrorCode errorCode) {
        this(
                errorCode.getStatus().value(),
                errorCode.getStatus().getReasonPhrase(),
                errorCode.getMessage(),
                LocalDateTime.now()
        );
    }
    public ErrorResponse( UserErrorCode errorCode) {
        this(
                errorCode.getStatus().value(),
                errorCode.getStatus().getReasonPhrase(),
                errorCode.getMessage(),
                LocalDateTime.now()
        );
    }


}
