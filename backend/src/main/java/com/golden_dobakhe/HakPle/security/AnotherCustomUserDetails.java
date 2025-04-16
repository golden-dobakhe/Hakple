package com.golden_dobakhe.HakPle.security;

//userdetails에 대하여 2가지로 나뉘어서 일단 합치가 보단 2개를 병렬 시켜놓고 이따 합쳐보는걸로
import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AnotherCustomUserDetails implements UserDetails {
    private final User user;

    public AnotherCustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + user.getStatus().name(); // 예: ROLE_ACTIVE
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    public Long getUserId(){
        return user.getId();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
