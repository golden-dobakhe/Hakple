package com.golden_dobakhe.HakPle.security;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.global.entity.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.Collection;
import java.util.List;

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
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
