package com.golden_dobakhe.HakPle.domain.user.myInfo.service;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoRequestDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.repository.MyInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final MyInfoRepository myInfoRepository;

    public MyInfoRequestDto getMyInfo(String userName) {
        User user = myInfoRepository.findByUserName(userName);

        return MyInfoRequestDto.builder()
                .nickname(user.getNickName())
                .username(user.getUserName())
                .phoneNum(user.getPhoneNum())
                .creationTime(user.getCreationTime())
                .academyId(user.getAcademyId())
                .build();
    }
}
