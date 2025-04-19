package com.golden_dobakhe.HakPle.domain.user.admin.dto;

import com.golden_dobakhe.HakPle.domain.post.comment.report.entity.CommentReport;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentReportDto {
    @Schema(description = "신고 ID", example = "1")
    private Long reportId;

    @Schema(description = "신고된 댓글 ID", example = "42")
    private Long commentId;

    @Schema(description = "신고된 댓글 내용", example = "이 댓글은 부적절합니다.")
    private String commentContent;

    @Schema(description = "댓글 작성자 ID", example = "5")
    private Long reportedUserId;

    @Schema(description = "게시물 ID")
    private Long boardId;

    @Schema(description = "해당 사용자가 받은 총 신고 횟수", example = "3")
    private int userReportedCount;

    @Schema(description = "이 댓글이 받은 신고 횟수", example = "3")
    private int commentReportedCount;

    @Schema(description = "신고 일시", example = "2025-04-19T12:34:56")
    private LocalDateTime reportedAt;

    public static CommentReportDto fromEntity(CommentReport report,int commentReportCount) {
        User user= report.getComment().getUser();
        Board board = report.getComment().getBoard();
        return CommentReportDto.builder()
                .reportId(report.getId())
                .commentId(report.getComment().getId())
                .commentContent(report.getComment().getContent())
                .reportedUserId(report.getComment().getUser().getId())
                .userReportedCount(user.getReportedCount())
                .reportedAt(report.getCreationTime())
                .commentReportedCount(commentReportCount)
                .boardId(board.getId())
                .build();
    }

}

