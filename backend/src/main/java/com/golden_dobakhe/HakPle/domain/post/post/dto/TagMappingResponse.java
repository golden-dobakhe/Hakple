package com.golden_dobakhe.HakPle.domain.post.post.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TagMappingResponse {

    private Long mappingId;
    private String boardId;
    private String academyCode;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
