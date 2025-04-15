package com.golden_dobakhe.HakPle.domain.post.comment.like.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@AllArgsConstructor
@Schema(description = "유저가 좋아요를 누른 댓글 정보 DTO")
public class LikedCommentDto {

    @Schema(description = "댓글 ID", example = "123")
    private Long commentId;

    @Schema(description = "댓글 내용", example = "이 댓글 정말 좋아요!")
    private String content;
}
