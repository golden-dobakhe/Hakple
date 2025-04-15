package com.golden_dobakhe.HakPle.security;

import com.golden_dobakhe.HakPle.domain.user.entity.User;

import lombok.Getter;

import com.golden_dobakhe.HakPle.global.entity.Status;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


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
