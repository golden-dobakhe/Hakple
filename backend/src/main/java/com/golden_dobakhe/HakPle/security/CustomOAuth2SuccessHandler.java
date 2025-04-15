package com.golden_dobakhe.HakPle.security;


import com.golden_dobakhe.HakPle.domain.user.user.repository.UserRepository;
import com.golden_dobakhe.HakPle.security.service.TestAuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


//로그인 처리하고 프론트에게 던져주는거
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final TestAuthService userService;
    private final UserRepository userRepository;
    private final Rq rq;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //유저를 가져오고
//        User user = userRepository.findById(rq.getActor().getId()).get();

        //토큰을 발급
//        rq.Makecookies(actor);
        String redirectUrl = AppConfig.getSiteFrontUrl();

        //프론트 주소로 리다이렉트를 시켜줌
        response.sendRedirect(redirectUrl);
    }
}
