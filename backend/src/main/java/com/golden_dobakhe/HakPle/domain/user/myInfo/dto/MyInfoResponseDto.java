package com.golden_dobakhe.HakPle.domain.user.myInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "사용자 정보 응답 DTO")
public class MyInfoResponseDto { //사용자 정보 조회용

    @Schema(description = "사용자 닉네임", example = "lion_123")
    private String nickname;

    @Schema(description = "사용자 아이디", example = "라이온")
    private String username;

    @Schema(description = "사용자 전화번호", example = "01012345678")
    private String phoneNum;

    @Schema(description = "계정 생성 시각")
    private LocalDateTime creationTime;

    @Schema(description = "등록된 학원 코드", example = "LLN1234XYZ")
    private String academyCode;
}
