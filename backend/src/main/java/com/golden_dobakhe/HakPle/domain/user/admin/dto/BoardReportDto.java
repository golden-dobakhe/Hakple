package com.golden_dobakhe.HakPle.domain.user.admin.dto;

import com.golden_dobakhe.HakPle.domain.post.post.entity.BoardReport;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "신고된 게시물 정보 DTO")
public class BoardReportDto {

    @Schema(description = "신고 ID", example = "1")
    private Long reportId;

    @Schema(description = "신고된 게시글  ID", example = "42")
    private Long boardId;

    @Schema(description = "신고된 게시글 제목", example = "제목입니다")
    private String boardTitle;

    @Schema(description = "댓글 작성자 ID", example = "5")
    private Long reportedUserId;

    @Schema(description = "해당 사용자가 받은 총 신고 횟수", example = "3")
    private int userReportedCount;

    @Schema(description = "이 게시글이 받은 신고 횟수", example = "4")
    private int boardReportedCount;

    @Schema(description = "신고 일시", example = "2025-04-19T12:34:56")
    private LocalDateTime reportedAt;

    public static BoardReportDto fromEntity(BoardReport report,int boardReportCount) {
        User user = report.getBoard().getUser();
        return new BoardReportDto(
                report.getId(),
                report.getBoard().getId(),
                report.getBoard().getTitle(),
                user.getId(),
                user.getReportedCount(),
                boardReportCount,
                report.getCreationTime()
        );
    }
}

