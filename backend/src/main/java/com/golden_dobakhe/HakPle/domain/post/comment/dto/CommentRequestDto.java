package com.golden_dobakhe.HakPle.domain.post.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "댓글 요청 DTO")
public class CommentRequestDto {

    @Schema(description = "게시글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long boardId;

    @Schema(description = "댓글 내용", example = "이 게시글 정말 좋네요!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "댓글 작성자 ID", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commenterId;
}
