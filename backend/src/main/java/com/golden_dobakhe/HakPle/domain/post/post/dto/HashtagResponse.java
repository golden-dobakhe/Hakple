package com.golden_dobakhe.HakPle.domain.post.post.dto;

import com.golden_dobakhe.HakPle.domain.post.post.entity.Hashtag;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HashtagResponse {

    private Long id;
    private String hashtagName;
    private String academyCode;


    public static HashtagResponse convertDto(Hashtag hashtag) {
        if (hashtag == null) {
            return null;
        }

        return HashtagResponse.builder()
                .id(hashtag.getId()) // id 필드도 포함
                .hashtagName(hashtag.getHashtagName())
                .academyCode(hashtag.getAcademyCode())
                .build();
    }
}
