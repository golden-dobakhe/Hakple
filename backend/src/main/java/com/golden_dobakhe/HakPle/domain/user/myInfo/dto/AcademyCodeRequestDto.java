package com.golden_dobakhe.HakPle.domain.user.myInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "학원 코드 등록 요청 DTO")
public class AcademyCodeRequestDto {
    @Schema(description = "등록할 학원 코드", example = "LLN1234XYZ")
    private String academyId;
}
