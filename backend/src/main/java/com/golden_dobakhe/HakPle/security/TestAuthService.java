package com.golden_dobakhe.HakPle.security;
//이 부분은 테스트를 위한 것이며 추후 어딘가에 병합이 될 수 있음

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.security.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestAuthService {
    private final TestRepository testRepository;

    //일단 간단하게 있는지 없는지 체크
    public User findByUserName(LoginDto dto) {
        return testRepository.findByUserName(dto.getUsername());
    }

    public void addRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        testRepository.save(user);
    }

}
