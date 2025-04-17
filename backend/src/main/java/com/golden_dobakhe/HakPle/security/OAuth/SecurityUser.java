package com.golden_dobakhe.HakPle.security.OAuth;

import com.golden_dobakhe.HakPle.global.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

//얘는 일단 시큐리티에게 넘겨주는 역할
//여기서 User는 엔티티 유저가 아닌 시큐리티에서 정의된 User
@Getter
public class SecurityUser extends User implements OAuth2User {
    private Long id;
    private String nickname;
    private Status status;

    public SecurityUser(
    Long id,
    String username,
    String password,
    String nickname,
    Status status,
    Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
        this.id = id;
        this.nickname = nickname;
        this.status = status;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return "";
    }
}
