package com.golden_dobakhe.HakPle.domain.user.service;


import com.golden_dobakhe.HakPle.domain.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//회원가입 로직
    public void register(UserDTO userDTO) {
        // 사용자 이름 중복 확인
        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new IllegalArgumentException("Username already exists!");
        }

        // User 엔티티로 변환 및 저장
        User user = User.builder()
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword())) // 비밀번호 암호화
                .nickName(userDTO.getNickName())
                .phoneNum(userDTO.getPhoneNumber())
                .status(Status.PENDING) // 기본 상태 설정
                .build();

        userRepository.save(user);
    }


//사용자 조회 (로그인 로직에서 사용)

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}