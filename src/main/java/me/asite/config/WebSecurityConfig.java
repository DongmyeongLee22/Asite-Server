package me.asite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 스프링 시큐리티 적용
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 인증상태에 따라 허용되는 웹페이지 설정
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/","/student/join").permitAll()
                .anyRequest().authenticated()
                .and()
             .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
             .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }
}
