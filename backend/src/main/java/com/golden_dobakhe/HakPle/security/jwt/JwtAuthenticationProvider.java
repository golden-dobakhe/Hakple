package com.golden_dobakhe.HakPle.security.jwt;

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.global.Status;
import com.golden_dobakhe.HakPle.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public Authentication getAuthentication(String token) {
        Claims claims;
        try {
            claims = jwtTokenizer.parseAccessToken(token);
        } catch (ExpiredJwtException e) {
            log.warn("🔐 만료된 토큰 사용 시도: {}", e.getMessage());
            throw new RuntimeException("토큰이 만료되었습니다", e);
        }

        // ✅ Redis 블랙리스트 확인 (로그아웃 토큰 여부)
        if (redisTemplate.hasKey(token)) {
            log.warn("🚫 로그아웃된 토큰 사용 시도: {}", token);
            throw new RuntimeException("로그아웃된 토큰입니다");
        }

        // ✅ 토큰에서 정보 파싱
        String userName = claims.getSubject();
        Object userIdRaw = claims.get("userId");
        Long userId = null;

        if (userIdRaw instanceof Integer) {
            userId = ((Integer) userIdRaw).longValue();
        } else if (userIdRaw instanceof Long) {
            userId = (Long) userIdRaw;
        } else if (userIdRaw instanceof String) {
            userId = Long.parseLong((String) userIdRaw);
        }

        if (userId == null) {
            throw new IllegalStateException("JWT에 userId가 없습니다!");
        }

        // ✅ DB에서 유저 상태 확인 (탈퇴/정지 여부)
        User user = userRepository.findById(userId).orElse(null);
        if (user == null || user.getStatus() != Status.ACTIVE) {
            log.warn("🚫 비활성화된 사용자 또는 존재하지 않는 사용자 (userId: {})", userId);
            throw new RuntimeException("접근 불가: 탈퇴 또는 정지된 계정입니다");
        }

        String nickname = claims.get("nickname", String.class);
        String statusStr = claims.get("status", String.class);
        Status status = Status.valueOf(statusStr);

        // ✅ UserDetails 생성
        User userForPrincipal = User.builder()
                .userName(userName)
                .nickName(nickname)
                .status(status)
                .password("N/A")
                .id(userId)
                .build();

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + statusStr.toUpperCase())
        );

        log.info("✅ 사용자 인증 완료: userId = {}", userId);
        CustomUserDetails customUserDetails = new CustomUserDetails(userForPrincipal);

        return new JwtAuthenticationToken(authorities, customUserDetails, null);
    }
}
