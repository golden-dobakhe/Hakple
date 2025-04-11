package com.golden_dobakhe.HakPle.domain.post.comment;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 처리 결과 코드")
public enum CommentDeleteResult {

    @Schema(description = "사용자를 찾지 못함")
    USER_NOT_FOUND,

    @Schema(description = "댓글을 찾지 못함")
    COMMENT_NOT_FOUND,

    @Schema(description = "게시글을 찾지 못함")
    BOARD_NOT_FOUND,

    @Schema(description = "권한 없음")
    UNAUTHORIZED,

    @Schema(description = "댓글 내용이 비어 있음")
    EMPTY,

    @Schema(description = "성공적으로 처리됨")
    SUCCESS
}
