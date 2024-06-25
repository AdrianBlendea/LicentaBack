package com.example.programingappapi.config;


import com.example.programingappapi.jwtutil.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/categories").permitAll()
                        .requestMatchers("/documents/all/noauth/*").permitAll()
                        .requestMatchers("/api/type/all").permitAll()
                        .requestMatchers("/api/problem/type/*").permitAll()
                        .requestMatchers("/api/register").permitAll()
                        .requestMatchers("/statistics/leaderboard").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/v2/api-docs/**","/api")
                        .permitAll()
                        .requestMatchers("/api/confirm-account*").permitAll()
                        .anyRequest().authenticated())
                .csrf().disable();


        return http.build();
    }


}