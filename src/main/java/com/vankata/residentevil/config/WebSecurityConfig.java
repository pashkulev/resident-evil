package com.vankata.residentevil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PERMITTED_ROUTES = {"/", "/users/login", "/users/register"};

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(PERMITTED_ROUTES).permitAll()
            .anyRequest().authenticated()
        .and()
            .formLogin().loginPage("/users/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
        .and()
            .logout()
                .logoutUrl("/users/logout")
                .permitAll()
        .and()
            .rememberMe()
                .rememberMeParameter("remember")
                .key("uniqueAndSecret")
                .rememberMeCookieName("rememberMeCookie")
                .tokenValiditySeconds(1000)
        .and()
            .exceptionHandling()
                .accessDeniedPage("/unauthorized")
        .and()
            .userDetailsService(this.userDetailsService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**");
    }
}
