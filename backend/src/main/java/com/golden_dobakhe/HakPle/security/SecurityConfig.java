package com.golden_dobakhe.HakPle.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        //접근 제한
        security
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/success", "/failure", // 기존
                                "/swagger-ui/**",            // Swagger UI
                                "/v3/api-docs/**",           // OpenAPI JSON
                                "/swagger-resources/**",     // Swagger 리소스
                                "/webjars/**"                // Swagger static
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        // 로그인
        security
                .formLogin(form -> form
                        .defaultSuccessUrl("/success")
                        .failureUrl("/failure")
                );

        // 로그아웃 (필요시 여기에 추가)

        return security.build();
    }
}