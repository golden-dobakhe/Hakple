package com.golden_dobakhe.HakPle.domain.post.post.dto;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.global.entity.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private Status status;
    private String academyCode;
    private String userNickname;
    private List<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public BoardResponse(Board board, List<String> tags) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount();
        this.likeCount = board.getBoardLikes().size();
        this.status = board.getStatus();
        this.academyCode = board.getAcademyCode();
        this.userNickname = board.getUser().getNickName();
        this.tags = tags;
        this.createdAt = board.getCreationTime();
        this.updatedAt = board.getModificationTime();
    }
}
