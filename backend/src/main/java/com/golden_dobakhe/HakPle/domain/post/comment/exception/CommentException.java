package com.golden_dobakhe.HakPle.domain.post.comment.exception;

import com.golden_dobakhe.HakPle.domain.post.comment.CommentResult;
import lombok.Getter;

@Getter
public class CommentException extends RuntimeException {

    private final CommentResult result;

    public CommentException(CommentResult result) {
        super(result.name()); // 예외 메시지로 enum 이름 사용
        this.result = result;
    }
}