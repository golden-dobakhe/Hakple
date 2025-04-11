package com.golden_dobakhe.HakPle.domain.user.myInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "학원 정보 요청 DTO")
public class MyInfoUpdateRequestDto { //사용자 정보 수정용

    @Schema(description = "수정할 닉네임", example = "lion_123")
    private String nickName;

    @Schema(description = "수정할 전화번호", example = "01012345678")
    private String phoneNum;

    @Schema(description = "등록할 학원 코드", example = "ABC1234XYZ")
    private String academyId;
}
