package com.golden_dobakhe.HakPle.domain.post.post.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardRequest {
    private String title;
    private String content;
    private String academyCode;
    private List<String> tags;


    @Builder
    public BoardRequest(String title, String content, String academyCode, List<String> tags) {
        this.title = title;
        this.content = content;
        this.academyCode = academyCode;
        this.tags = (tags != null) ? tags : new ArrayList<>();
    }
}
