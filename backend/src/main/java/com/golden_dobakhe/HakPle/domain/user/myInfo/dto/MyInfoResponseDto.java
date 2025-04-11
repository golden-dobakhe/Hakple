package com.golden_dobakhe.HakPle.domain.user.myInfo.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyInfoResponseDto { //사용자 정보 조회용
    private String nickname;
    private String username;
    private String phoneNum;
    private LocalDateTime creationTime;
    private String academyId;
}
