package com.kiseok.sessionskeleton.config;

import com.kiseok.sessionskeleton.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity          // Spring Security 설정을 활성화 시켜주는 애노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Cross Site Request Forgery(사이트 간 요청 위조) 기능을 비활성화 시켜서 h2-console에 접근하기 위한 설정
        http
                    .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"))
                .and()
                    .headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"))
                    .frameOptions().disable();

        // URL 별로 권한 관리를 설정
        http
                    .authorizeRequests().antMatchers("/sign-up", "/sign-up-form", "/h2-console/**").permitAll()
                    .anyRequest().authenticated();

        // form-login 설정
        http
                    .formLogin()
                    .loginPage("/sign-in")
                    .permitAll();

        // 로그아웃 관련 설정
        http
                    .logout()
                    .logoutSuccessUrl("/sign-in")
                    .deleteCookies("JSESSIONID");

        // OAuth2 로그인에 대한 여러 설정의 진입점
        http
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(accountService)
        ;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/favicon.ico")
        ;
    }

}
