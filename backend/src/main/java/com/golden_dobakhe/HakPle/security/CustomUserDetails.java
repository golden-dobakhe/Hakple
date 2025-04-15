package com.golden_dobakhe.HakPle.security;

import com.golden_dobakhe.HakPle.domain.user.entity.User;

import lombok.Getter;

import com.golden_dobakhe.HakPle.global.entity.Status;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


//user객체만 가져오는 방식이랑 원하는 부분만 가져오는 방식에서 문제가 생겨서 이렇게 나누었습니다
@Getter
public class CustomUserDetails implements UserDetails {
    private final String username;
    private final String nickname;
    private final Status status;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(String username, String nickname, Status status, List<GrantedAuthority> roles) {
        this.username = username;
        this.nickname = nickname;
        this.status = status;
        this.authorities = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return "";

    }

    @Override
    public String getUsername() {

        return this.username; // 유저 이름

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
