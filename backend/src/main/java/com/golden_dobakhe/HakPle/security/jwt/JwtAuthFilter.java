package com.golden_dobakhe.HakPle.security.jwt;

//시큐리티에게 jwt를 넘겨주기 위한 필터


import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.global.Status;
import com.golden_dobakhe.HakPle.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;



    //시큐리티가 실행되기 이전 토큰을 시큐리티에게 알려주는 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


//        //만약에 유효한 부분이 아니면 나가리
//        if (!request.getRequestURI().startsWith("/api/")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //만약에 필터링을 거치지 말아야 한 부분이면 나가리
//        if (List.of("/api/v1/members/login", "/api/v1/members/logout", "/api/v1/members/join").contains(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        //실제 실행부
        //여기서 uri 보고 토큰을 필요로 하지 않는 작업이라면 넘어가게 만든다


        //토큰을 받아오고
        String token = getTokken(request);

        //토큰이 없다면 그대로 진행(로그인해서 토큰을 재발급가능)
        if (token == null) {
            filterChain.doFilter(request, response);
            return ;
        }

        try {
            Authentication authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        //이후 그걸 가지고 알아서 진행
        filterChain.doFilter(request, response);
    }
    //인증이 필요한 요청시 헤더에 Authorization Bearer jwt토큰내용 이렇게 나오게 된다
    private String getTokken(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (StringUtils.hasText(auth) && auth.startsWith("Bearer "))
            return auth.substring(7);

        //쿠키로 받았다면?
        //쿠키는 그냥 가지고 있다고 받아와지나
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //name에 저걸 넣었으니 일치한놈만 고른다
                if (cookie.getName().equals("accessToken"))
                    return cookie.getValue();
            }
        }

        return null;
    }

    private Authentication getAuthentication(String token) {
        Claims claims;
        try {
            claims = jwtTokenizer.parseAccessToken(token);
        } catch (ExpiredJwtException e) {
            log.warn("🔐 만료된 토큰 사용 시도: {}", e.getMessage());
            throw new RuntimeException("토큰이 만료되었습니다", e); // 또는 return null; 후 SecurityException 던져도 가능
        }

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

        String nickname = claims.get("nickname", String.class);
        String statusStr = claims.get("status", String.class);
        Status status = Status.valueOf(statusStr);

        User user = User.builder()
                .userName(userName)
                .nickName(nickname)
                .status(status)
                .password("N/A")
                .id(userId)
                .build();

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + statusStr.toUpperCase())
        );

        log.info(">>> userId: " + userId);
        log.info(">>> JWT Claims: " + claims);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return new JwtAuthenticationToken(authorities, customUserDetails, null);
    }

}
