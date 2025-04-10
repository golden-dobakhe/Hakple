package com.golden_dobakhe.HakPle.domain.post.post.dto;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Board;
import com.golden_dobakhe.HakPle.domain.post.post.entity.Hashtag;
import com.golden_dobakhe.HakPle.global.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private Status status;
    private String academyCode;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int likes;
    private List<HashtagResponse> hashtags;

    public static BoardResponseDto convertToDto(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .academyCode(board.getAcademyCode())
                .status(board.getStatus())
                .createdAt(board.getCreationTime())
                .modifiedAt(board.getModificationTime())
                .viewCount(board.getViewCount())
                .likes(board.getLikes().size())
                .hashtags(
                        board.getHashtags().stream()
                                .map(HashtagResponse::convertDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
