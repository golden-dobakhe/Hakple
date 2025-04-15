package com.golden_dobakhe.HakPle.domain.user.user.service;


import com.golden_dobakhe.HakPle.domain.user.user.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 로직 (중복 확인 포함)
    public void register(UserDTO userDTO) {
        // 사용자 이름 중복 확인
        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new IllegalArgumentException("아이디가 이미 사용 중입니다.");
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickName(userDTO.getNickName())) {
            throw new IllegalArgumentException("닉네임이 이미 사용 중입니다.");
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

    //비밀번호 변경 (로그인한 사용자가 내 정보에서 비밀번호 변경)
    public void changePasswordWithOldPassword(Long userId, String currentPassword, String newPassword, String newPasswordConfirm) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 현재 비밀번호 일치 확인
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new UserException(UserErrorCode.WRONG_CURRENT_PASSWORD);
        }

        // 새 비밀번호 확인
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new UserException(UserErrorCode.PASSWORD_CONFIRM_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    //비밀번호 찾기 후 문자 인증이 완료된 경우 비밀번호 재설정
    public void resetPassword(Long userId, String newPassword, String newPasswordConfirm) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        // 새 비밀번호 확인
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new UserException(UserErrorCode.PASSWORD_CONFIRM_NOT_MATCH);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }


}