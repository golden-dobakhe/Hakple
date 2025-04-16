package com.golden_dobakhe.HakPle.security.service;
//이 부분은 테스트를 위한 것이며 추후 어딘가에 병합이 될 수 있음


import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.security.TestRepository;
import com.golden_dobakhe.HakPle.security.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestAuthService {
    private final TestRepository userRepository;

    //일단 간단하게 있는지 없는지 체크
    public User findByUserName(LoginDto dto) {
        return userRepository.findByUserName(dto.getUsername());
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public void addRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }
    public User join(String username, String password, String nickname) {

        if (userRepository.findByUserName(username) != null) {
            throw new RuntimeException("해당 username은 이미 사용중입니다.");
        };

        User user = User.builder()
                .userName(username)
                .password(password)
                .nickName(nickname)
                .socialProvider("kakao")
                .build();

        return userRepository.save(user);
    }

    public User modifyOrJoin(String username, String nickname) {
        User user = userRepository.findByUserName(username);

        //만약에 있다면 수정
        if (user != null) {

            user.setNickName(nickname);
            return user;
        }
        //핸드폰 번호는 없다
        //소셜로그인계정으로 로그인시 아이디,비밀번호를 까먹었다면 해당 소셜 서비스에서 바꾸는게 나을듯
        //없으면 참가
        return join(username, "", nickname);
    }

}
