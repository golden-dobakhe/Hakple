package com.golden_dobakhe.HakPle.security.OAuth;

import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.security.SecurityUser;
import com.golden_dobakhe.HakPle.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


//응답, 요청, 쿠키, 세션등을 다룬다
@RequestScope
@Component
@RequiredArgsConstructor
public class CustomRequest {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final AuthService authService;

    //로그인 처리를 위하여 SecurityContextHolder에 유저를 넣음
    public void setLogin(User member) {
        //userDetails와 구분하기 위하여 이렇게 썼습니다
        UserDetails user = new SecurityUser(
                member.getId(),
                member.getUserName(),
                "",
                member.getNickName(),
                member.getStatus(),
                List.of(new SimpleGrantedAuthority("ROLE_" + member.getStatus().getValue().toUpperCase()))

        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                user.getPassword(),
                user.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //로그인한 사용자 정보 가져오기
    public User getActor() {
        return Optional.ofNullable(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                )
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof SecurityUser)
                .map(principal -> (SecurityUser) principal)
                //원본은 그냥 유저 객체를 생성하는데, 엔티티에 해당 생성자를 만드는 것 보다 findById가 더 구현하기 깔끔하다고 생각
                //하지만 속도를 생각한다면 그냥 생성자 하나 만드는게 더 빠를듯
                .map(securityUser -> authService.findById(securityUser.getId()).get())
                .orElse(null);
    }

    //쿠키 세팅
    public void setCookie(String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .domain("localhost")
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .build();
        resp.addHeader("Set-Cookie", cookie.toString());
    }

    //쿠키 가져오기(이미 쿠키가 있다면)
    public String getCookieValue(String name) {
        return Optional
                .ofNullable(req.getCookies())
                .stream() // 1 ~ 0
                .flatMap(cookies -> Arrays.stream(cookies))
                .filter(cookie -> cookie.getName().equals(name))
                .map(cookie -> cookie.getValue())
                .findFirst()
                .orElse(null);
    }

    //쿠키 삭제하기(로그아웃시 필요한들?)
    public void deleteCookie(String name) {
        ResponseCookie cookie = ResponseCookie.from(name, null)
                .path("/")
                .domain("localhost")
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .maxAge(0)
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }

    //헤더 설정
    public void setHeader(String name, String value) {
        resp.setHeader(name, value);
    }

    //헤서 조회
    public String getHeader(String name) {
        return req.getHeader(name);
    }

    //토큰 만들고 헤더에 등록시키기
    //얜 jwt인가
//    public void refreshAccessToken(Member member) {
//        String newAccessToken = memberService.genAccessToken(member);
//
//        setHeader("Authorization", "Bearer " + member.getApiKey() + " " + newAccessToken);
//        setCookie("accessToken", newAccessToken);
//    }

    //인증 쿠키 만들기
    public String makeAuthCookies(User user) {
        //대충 서비스에서 토큰을 생성하는 메서드를 생성
        String accessToken = authService.genAccessToken(user);

        //api키는 없이 토큰만 있다고 칩시다
        //setCookie("apiKey", member.getApiKey());
        setCookie("accessToken", accessToken);

        return accessToken;
    }
}
