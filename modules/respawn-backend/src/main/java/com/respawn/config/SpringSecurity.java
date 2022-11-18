package com.respawn.config;

import com.respawn.CustomAuthenticationProvider;
import com.respawn.services.RespawnUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/img/**", "/styles/**", "/api/v1/**", "/search", "/detalle/**", "/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .usernameParameter("email")
            .passwordParameter("password")
            .loginProcessingUrl("/doLogin")
            .loginPage("/login")
            .defaultSuccessUrl("/", true)
            .permitAll()
            .and()
            .rememberMe().key("rem-me-key")
            .tokenValiditySeconds(86400)
            .and()
            .logout()
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and().csrf().disable();
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new RespawnUserDetailsService();
    }
    
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}


