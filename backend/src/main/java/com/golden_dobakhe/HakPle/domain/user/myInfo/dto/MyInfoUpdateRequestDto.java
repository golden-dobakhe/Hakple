package com.golden_dobakhe.HakPle.domain.user.myInfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyInfoUpdateRequestDto { //사용자 정보 수정용
    private String nickName;
    private String phoneNum;
    private String academyId;
}
