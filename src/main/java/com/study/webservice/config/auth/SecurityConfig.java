package com.study.webservice.config.auth;

import com.study.webservice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity //Spring Security 설정 활성화 어노테이션
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //h2-console 기능 활성화를 위해 disable 설정
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                //URL별 권한 관리를 설정하는 옵션의 시발점
                .authorizeRequests()
                //권한 관리 대상을 지정한다
                .antMatchers("/", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                //antMatchers로 설정한 URL을 제외한 URL들
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                //OAuth2 로그인 성공 이후 사용자 정보를 가져올때의 설정을 담당
                .userInfoEndpoint()
                //소셜 로그인 성공시 후속 조치를 담당할 UserService 인터페이스의 구현체를 등록한다
                .userService(customOAuth2UserService);
    }
}
