package com.golden_dobakhe.HakPle.security;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("jwt인증 위한 유저가 없는뎁쇼");

        return new SecurityUser(
                user.getId(),
                user.getUserName(),
                "",
                user.getNickName(),
                user.getStatus(),
                Collections.emptyList()

        );
    }
}
