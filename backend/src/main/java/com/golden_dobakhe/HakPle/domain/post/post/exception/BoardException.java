package com.golden_dobakhe.HakPle.domain.post.post.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BoardException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public BoardException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static BoardException notFound() {
        return new BoardException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
    }

    public static BoardException unauthorized() {
        return new BoardException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
    }

    public static BoardException invalidRequest() {
        return new BoardException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    }

    public static BoardException duplicateTag() {
        return new BoardException(HttpStatus.CONFLICT, "이미 존재하는 태그입니다.");
    }
}
