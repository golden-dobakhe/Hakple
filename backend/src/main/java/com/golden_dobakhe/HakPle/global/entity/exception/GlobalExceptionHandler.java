package com.golden_dobakhe.HakPle.global.entity.exception;

import com.golden_dobakhe.HakPle.domain.post.post.exception.BoardException;
import com.golden_dobakhe.HakPle.domain.resource.image.exception.ProfileImageException;
import com.golden_dobakhe.HakPle.domain.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.myInfo.exception.MyInfoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<ErrorResponse> handleBoardException(BoardException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MyInfoException.class)
    public ResponseEntity<ErrorResponse> handleMyInfoException(MyInfoException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(ProfileImageException.class)
    public ResponseEntity<ErrorResponse> handleProfileImageException(ProfileImageException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getErrorCode().getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getErrorCode().getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse("서버 오류가 발생했습니다."));
    }

    public record ErrorResponse(String message) {
    }
}