package com.golden_dobakhe.HakPle.domain.user.user.service;


import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.user.WithdrawResult;
import com.golden_dobakhe.HakPle.domain.user.user.dto.UserDTO;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.Status;
import com.golden_dobakhe.HakPle.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtTokenizer jwtTokenizer;

    // 회원가입 로직 (중복 확인 포함)
    public void register(UserDTO userDTO) {
        // 사용자 이름 중복 확인
        if (userRepository.existsByUserName(userDTO.getUserName())) {
            throw new UserException(UserErrorCode.USERNAME_DUPLICATE);
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickName(userDTO.getNickName())) {
            throw new UserException(UserErrorCode.NICKNAME_DUPLICATE);
        }


        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNum(userDTO.getPhoneNumber())) {
            throw new UserException(UserErrorCode.PHONENUM_DUPLICATE);
        }

        // User 엔티티로 변환 및 저장
        User user = User.builder()
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword())) // 비밀번호 암호화
                .nickName(userDTO.getNickName())
                .phoneNum(userDTO.getPhoneNumber())
                .status(Status.ACTIVE) // 기본 상태 설정
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

    //회원 탈퇴
    public WithdrawResult withdraw(Long userId, String rawPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return WithdrawResult.USER_NOT_FOUND;

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return WithdrawResult.WRONG_PASSWORD;
        }

        user.setStatus(Status.INACTIVE);
        userRepository.save(user);
        return WithdrawResult.SUCCESS;
    }

    //로그아웃
    public void logout(String accessToken, Long userId) {
        // ✅ Access Token → 블랙리스트 등록
        try {
            long remainingTime = jwtTokenizer.getRemainingTime(accessToken);
            redisTemplate.opsForValue().set(accessToken, "logout", remainingTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new UserException(UserErrorCode.ACCESS_TOKEN_BLACKLIST_FAIL);
        }

        // ✅ Refresh Token 삭제
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        try {
            user.setRefreshToken(null);
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserException(UserErrorCode.REFRESH_TOKEN_DELETE_FAIL);
        }
    }





}