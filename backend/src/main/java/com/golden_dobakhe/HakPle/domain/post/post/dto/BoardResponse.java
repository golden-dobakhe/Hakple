package com.golden_dobakhe.HakPle.domain.post.post.dto;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.global.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int commentCount;
    private Status status;
    private String academyCode;
    private String nickname;
    private List<String> tags;
    private LocalDateTime creationTime;
    private LocalDateTime modificationTime;

    public static BoardResponse from(Board board, List<String> tags) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCount(board.getViewCount())
                .likeCount(board.getBoardLikes() != null ? board.getBoardLikes().size() : 0)
                .commentCount(0)
                .status(board.getStatus())
                .academyCode(board.getAcademyCode())
                .nickname(board.getUser() != null ? board.getUser().getNickName() : null)
                .tags(tags)
                .creationTime(board.getCreationTime())
                .modificationTime(board.getModificationTime())
                .build();
    }

    public static BoardResponse from(Board board, List<String> tags, int commentCount) {
        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCount(board.getViewCount())
                .likeCount(board.getBoardLikes() != null ? board.getBoardLikes().size() : 0)
                .commentCount(commentCount)
                .status(board.getStatus())
                .academyCode(board.getAcademyCode())
                .nickname(board.getUser() != null ? board.getUser().getNickName() : null)
                .tags(tags)
                .creationTime(board.getCreationTime())
                .modificationTime(board.getModificationTime())
                .build();
    }
}
