package com.golden_dobakhe.HakPle.security.jwt;

//시큐리티에게 jwt를 넘겨주기 위한 필터



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;



    //시큐리티가 실행되기 이전 토큰을 시큐리티에게 알려주는 필터
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //만약에 유효한 부분이 아니면 나가리
        if (!request.getRequestURI().startsWith("/api/")) {
            filterChain.doFilter(request, response);
            return;
        }

        //실제 실행부
        //여기서 uri 보고 토큰을 필요로 하지 않는 작업이라면 넘어가게 만든다


        //토큰을 받아오고
        String token = getTokken(request);

        //토큰이 없다면 그대로 진행(로그인해서 토큰을 재발급가능)
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = jwtAuthenticationProvider.getAuthentication(token);
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


}
