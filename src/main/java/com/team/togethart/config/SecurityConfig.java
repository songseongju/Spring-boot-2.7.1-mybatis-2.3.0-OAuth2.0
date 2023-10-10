package com.team.togethart.config;
import com.team.togethart.config.oauth.PrinclpalOauth2UserService;
import com.team.togethart.repository.member.MemberMapper;
import com.team.togethart.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import static org.hibernate.criterion.Restrictions.and;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CorsConfig corsConfig;
    @Autowired
    private PrinclpalOauth2UserService defaultOAuth2UserService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()

                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(defaultOAuth2UserService);    // 구글 로그인이 완료된 뒤의 후처리가 필요함. Tip. 코드 X , (엑세스토큰 + 사용자프로필정보 O)

                // 1.코드받기(인증) 2.엑세스토큰(권한) 3.사용자 프로필 정보 가져오기
                // 4-1. 그정보를 토대로 회원가입을 자동으로 진행 로그인
                // 4-2. 추가적인 구성이 필요(이메일 , 전화번호 , 이름 ,아이디)

            /*   .authorizeRequests()
                .requestMatchers().permitAll()
                .antMatchers("/login","signup","access_denied","/resources./**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/user_access")   //로그인 성공
                .failureUrl("/access_denied")  // 로그인 실패
                .and()
                .csrf().disable()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");*/
    }
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());

    }



}
