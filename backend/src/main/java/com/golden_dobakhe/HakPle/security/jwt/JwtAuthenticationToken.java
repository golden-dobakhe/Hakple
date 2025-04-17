package com.golden_dobakhe.HakPle.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

//시큐리티 객체인 principal에 값을 넣어준다
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String token;
    private Object principal;
    private Object credentials;


    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                  Object principal, Object credentials) {
        //대충 권한인데 우린 안씀
        super(authorities);
        //유저의 정보는 여기에 저장된다
        this.principal = principal;
        //얜 장식
        this.credentials = credentials;
        //이때 인증 완료로 간주한다
            //근데 그냥 이렇게 인증 완료 시켜도 되는건가...
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
