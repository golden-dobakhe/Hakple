package com.golden_dobakhe.HakPle;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.security.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityTestUtils {
    public static void setAuthentication(User user) {
        CustomUserDetails principal = new CustomUserDetails(user);
        Authentication auth = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}
