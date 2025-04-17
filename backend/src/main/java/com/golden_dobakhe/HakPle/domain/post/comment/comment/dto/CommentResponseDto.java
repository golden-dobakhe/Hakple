package com.golden_dobakhe.HakPle.domain.post.comment.comment.dto;

import com.golden_dobakhe.HakPle.domain.post.comment.comment.entity.Comment;

import com.golden_dobakhe.HakPle.global.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private Long boardId;
    private String content;
    private String nickname;
    private int likeCount;
    private Long userId;
    private LocalDateTime creationTime;
    private LocalDateTime modificationTime;
    private Status status;

    public static CommentResponseDto fromEntity(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .boardId(comment.getBoard().getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getNickName())
                .likeCount(comment.getLikeCount())
                .userId(comment.getUser().getId())
                .creationTime(comment.getCreationTime())
                .modificationTime(comment.getModificationTime())
                .status(comment.getStatus())
                .build();
    }
} 