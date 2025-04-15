package com.golden_dobakhe.HakPle.security;

//리퀘스트 클래스
import com.golden_dobakhe.HakPle.domain.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Optional;

// Request/Response 를 추상화한 객체
// Request, Response, Cookie, Session 등을 다룬다.
@RequestScope
@Component
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
//
//    public void setLogin(Member member) {
//        UserDetails user = new SecurityUser(
//                member.getId(),
//                member.getUsername(),
//                "",
//                member.getNickname(),
//                member.getAuthorities()
//        );
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                user,
//                user.getPassword(),
//                user.getAuthorities()
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//    }

    //시큐리티에서 유저의 정보를 가져온다
//    public User getActor() {
//        return Optional.ofNullable(
//                        SecurityContextHolder
//                                .getContext()
//                                .getAuthentication()
//                )
//                .map(Authentication::getPrincipal)
//                .filter(principal -> principal instanceof com.golden_dobakhe.HakPle.security.SecurityUser)
//                .map(principal -> (com.golden_dobakhe.HakPle.security.SecurityUser) principal)
//                .map(securityUser -> new User(securityUser.getId(), securityUser.getUserName(), securityUser.getNickName()))
//                .orElse(null);
//    }

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

    public void setHeader(String name, String value) {
        resp.setHeader(name, value);
    }

    public String getHeader(String name) {
        return req.getHeader(name);
    }

//    public void refreshAccessToken(Member member) {
//        String newAccessToken = memberService.genAccessToken(member);
//
//        setHeader("Authorization", "Bearer " + member.getApiKey() + " " + newAccessToken);
//        setCookie("accessToken", newAccessToken);
//    }
}