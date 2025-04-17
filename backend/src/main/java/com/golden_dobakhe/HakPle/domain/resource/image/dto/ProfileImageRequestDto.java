package com.golden_dobakhe.HakPle.domain.resource.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "프로필 이미지 업록드 요청 DTO")
public class ProfileImageRequestDto {
    @Schema(description = "사용자 아이디", example = "user")
    private String userName;
}
