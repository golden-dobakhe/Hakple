package com.golden_dobakhe.HakPle;

import com.golden_dobakhe.HakPle.domain.user.entity.User;
import com.golden_dobakhe.HakPle.security.AnotherCustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityTestUtils {
    public static void setAuthentication(User user) {
        AnotherCustomUserDetails principal = new AnotherCustomUserDetails(user);
// =======
//         CustomUserDetails principal = new CustomUserDetails(user);
// >>>>>>> develop
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
