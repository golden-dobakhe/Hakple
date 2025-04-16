package com.golden_dobakhe.HakPle.security.OAuth;


import com.golden_dobakhe.HakPle.domain.user.user.entity.User;
import com.golden_dobakhe.HakPle.security.service.AuthService;
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
    private final AuthService authService;
    private final CustomRequest customRequest;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //유저를 가져오고
        User user = authService.findById(customRequest.getActor().getId()).get();

        //토큰을 발급하고 그걸 가지고 쿠키로 만들기
        customRequest.makeAuthCookies(user);
        //리다이렉트 url을 만들기, state는 국룰인가봐
        String redirectUrl = request.getParameter("state");

        //프론트 주소로 리다이렉트를 시켜줌
        response.sendRedirect(redirectUrl);
    }
}
