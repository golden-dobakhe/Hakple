package com.golden_dobakhe.HakPle.domain.post.comment.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "댓글 저장 응답 DTO")
public class CommentResponseDto {
    @Schema(description = "댓글 ID", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commentId;
    private String message;
}

