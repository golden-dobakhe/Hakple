package com.golden_dobakhe.HakPle.domain.user.myInfo.service;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoResponseDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.dto.MyInfoUpdateRequestDto;
import com.golden_dobakhe.HakPle.domain.user.myInfo.validator.NickNameValidator;
import com.golden_dobakhe.HakPle.domain.user.myInfo.validator.PhoneNumValidator;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyInfoService {
    private final UserRepository userRepository;

    //사용자 정보 가져오기
    public MyInfoResponseDto getMyInfo(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        return MyInfoResponseDto.builder()
                .nickname(user.getNickName())
                .username(user.getUserName())
                .phoneNum(user.getPhoneNum())
                .creationTime(user.getCreationTime())
                .academyId(user.getAcademyId())
                .build();
    }

    //사용자 정보 수정
    @Transactional
    public void updateMyInfo(String userName, MyInfoUpdateRequestDto requestDto) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        //nickName 변경
        String newNickName = requestDto.getNickName();
        if (newNickName != null) {
            NickNameValidator.validateNickName(newNickName, user.getNickName(), userRepository);
            user.setNickName(newNickName);
        }

        //PhoneNum 변경
        String newPhoneNum = requestDto.getPhoneNum();
        if (newPhoneNum != null) {
            PhoneNumValidator.validatePhoneNum(newPhoneNum, user.getPhoneNum(), userRepository);
            user.setPhoneNum(newPhoneNum);
        }
    }
}