package com.golden_dobakhe.HakPle.security;

import com.golden_dobakhe.HakPle.security.jwt.JwtAuthFilter;
import com.golden_dobakhe.HakPle.security.jwt.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        //접근 제한
        security
                .addFilterBefore(new JwtAuthFilter(jwtTokenizer), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/failure", "/login",
                                "/oauth2/authorization/kakao?redirectUrl=http://localhost:3000", //카카오 로그인
                                "/swagger-ui/**",            // Swagger UI
                                "/v3/api-docs/**",           // OpenAPI JSON
                                "/swagger-resources/**",     // Swagger 리소스
                                "/webjars/**"                // Swagger static
                                         ).permitAll()
                        .anyRequest().authenticated())

                .sessionManagement(session -> session
                        //세션을 저장하지 않는다 -> 세션을 사용하지 않겠다는 뜻 jwt인증을 쓸거니까
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //http베이직은 헤더에서 보안에 취약하고 쟤를 빼버리고, 다른 인증수단인 베어러(얜 이거 빼면 자동으로 지정됨)으로 한다고 한다
                //이후 요청시 헤더에 Authorization
                .httpBasic(httpBasic -> httpBasic.disable())
                //.oauth2Login( oauth -> oauth )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        //security.build();

        //로그인
        security
                .formLogin(form -> form.disable()
                );




        return  security.build();
    }

    //cors에 대한 설정
    //요청이 들어오면 어디까지 허용할꺼냐
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        //혀용을 어디까지 허용할꺼냐를 설정한다
        //헤더, 메서드, 오리진 HTTP메서드...
        config.addAllowedOrigin("*"); //origin는 naver,goole,localhost같은거
        config.addAllowedHeader("*"); //베어러는 여기에 포함된다
        config.addAllowedMethod("*");
        config.setAllowedMethods(List.of("GET","POST","DELETE"));
        //그리고 그걸 적용할 url에 내가 설정한 부분을 적용시킬거라고 한다
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
