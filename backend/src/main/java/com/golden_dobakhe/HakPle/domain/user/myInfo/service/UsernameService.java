package com.golden_dobakhe.HakPle.domain.user.myInfo.service;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.myInfo.repository.UsernameRepository;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsernameService {
    private final UsernameRepository usernameRepository;

    // 아이디(Username) 찾기
    public String findUserNameByPhoneNum(String nickName, String phoneNum) {
        return usernameRepository.findByNickNameAndPhoneNum(nickName, phoneNum)
                .map(User::getUserName)
                .orElseThrow(() -> new IllegalArgumentException("해당 전화번호로 등록된 아이디가 없습니다."));
    }
}