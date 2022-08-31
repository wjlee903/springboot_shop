package com.shop.shop.config;

import com.shop.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login")    // Set Login Page URL
                .defaultSuccessUrl("/") // Set URL to go to when login is successful
                .usernameParameter("email") // Specifies the email as the parameter name to use when logging in
                .failureUrl("/members/login/error") // Set the URL to go to when login fails
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // specifies logout URL
                .logoutSuccessUrl("/") // Set the URL to go to when logout is successful
        ;

        http.authorizeRequests()    // 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미
                .mvcMatchers("/", "/members/**", "item/**", "/images/**").permitAll()   // 모든 사용자가 인증 없이 접근 가능
                .mvcMatchers("/admin/**").hasRole("ADMIN")  // /admin으로 시작하는 경로는 ADMIN Role일 경우에만 접근 가능
                .anyRequest().authenticated()   // 그 외 나머지 경로들은 모두 인증을 요구하도록 설정
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증되지 않은 사용자가 리소스에 접근하였을 떄 수행되는 핸들러 등록
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");  // static 디렉터리의 하위 파일은 인증을 무시하도록 설정
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Spring Sucurity에서 인증은 AuthenticationManager를 통해 이루어지며
     * AuthenticationManagerBuilder가 AuthenticationManager를 생성
     * userDetailService를 구현하고 있는 객체로 memeverService를 지정해주며,
     * 비밀번호 암호화를 위해 passwordEncoder를 지정
     *
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }


}
