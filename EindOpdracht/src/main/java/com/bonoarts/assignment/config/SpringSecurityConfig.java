package com.bonoarts.assignment.config;

import com.bonoarts.assignment.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //HTTP Basic authentication
            .httpBasic()
                .and()
            .authorizeRequests()
                .antMatchers("/repairs/**").hasRole("Engineer")
                .antMatchers("/resources/**").hasAnyRole("Engineer", "backoffice")
                .antMatchers("/actions/**").hasRole("Engineer")
                .antMatchers("/admin/**").hasRole("Admin")
                .antMatchers("/users/**").hasRole("Admin")
                .antMatchers("/clients/**").hasRole("Administration")
                .antMatchers("/cars/**").hasRole("Administration")
                .anyRequest().authenticated()
                .and()
                .logout()
                .permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
            .csrf().disable()
            .formLogin().disable();
    }
}