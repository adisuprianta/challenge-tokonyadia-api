package com.enigma.challengetokonyadiaapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthEntryPoint authEntryPoint;
    private final AuthTokenFillter authTokenFillter;
    private final AuthAccessDenied authAccessDenied;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        return http.httpBasic().and().csrf().disable()
                .exceptionHandling()
                .accessDeniedHandler(authAccessDenied)
                .authenticationEntryPoint(authEntryPoint)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/swagger-ui/**","/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(authTokenFillter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
