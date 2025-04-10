package com.golden_dobakhe.HakPle.domain.post.post.dto;


import com.golden_dobakhe.HakPle.global.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    @NotNull(message = "유저 ID는 null일 수 없습니다.")
    private Long userId;

    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String content;

    @NotBlank(message = "학원 코드는 공백일 수 없습니다.")
    private String academyCode;

    private Status status; // 선택적 필드

    private List<String> hashtags;
}
