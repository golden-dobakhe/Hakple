package com.golden_dobakhe.HakPle.domain.user.admin.service;

import com.golden_dobakhe.HakPle.domain.user.admin.dto.AdminLoginDto;
import com.golden_dobakhe.HakPle.domain.user.admin.dto.AdminRegisterDto;
import com.golden_dobakhe.HakPle.domain.user.exception.UserErrorCode;
import com.golden_dobakhe.HakPle.domain.user.exception.UserException;
import com.golden_dobakhe.HakPle.domain.user.user.entity.Role;
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.Status;
import com.golden_dobakhe.HakPle.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    public String registerAdmin(AdminRegisterDto dto) {
        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new UserException(UserErrorCode.USERNAME_DUPLICATE);
        }
        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNum(dto.getPhoneNumber())) {
            throw new UserException(UserErrorCode.PHONENUM_DUPLICATE);
        }

        User admin = User.builder()
                .userName(dto.getUserName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickName(dto.getNickName())
                .phoneNum(dto.getPhoneNumber())
                .roles(new HashSet<>(Set.of(Role.ADMIN)))
                .status(Status.ACTIVE)
                .build();

        userRepository.save(admin);
        return "관리자 등록 완료";
    }

    public Map<String, String> loginAdmin(AdminLoginDto dto) {
        User admin = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new UserException(UserErrorCode.WRONG_CURRENT_PASSWORD);
        }

        if (!admin.getRoles().contains(Role.ADMIN)) {
            throw new UserException(UserErrorCode.FORBIDDEN);
        }

        String accessToken = jwtTokenizer.createAccessToken(
                admin.getId(),
                admin.getUserName(),
                admin.getNickName(),
                admin.getPhoneNum(),
                admin.getStatus(),
                admin.getRoles()
        );

        String refreshToken = jwtTokenizer.createRefreshToken(
                admin.getId(),
                admin.getUserName(),
                admin.getNickName(),
                admin.getPhoneNum(),
                admin.getStatus(),
                admin.getRoles()
        );

        // RefreshToken 저장
        admin.setRefreshToken(refreshToken);
        userRepository.save(admin);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }
}
