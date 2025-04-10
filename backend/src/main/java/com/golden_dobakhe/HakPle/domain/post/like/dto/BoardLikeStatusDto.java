package com.golden_dobakhe.HakPle.domain.post.like.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardLikeStatusDto {

    private boolean isLiked;
    private int totalLikes;


}
