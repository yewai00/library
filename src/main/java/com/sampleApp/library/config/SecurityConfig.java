package com.sampleApp.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PUBLIC_ROUTES = { "/api/v1/books/{id}/comments", };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> {
            request.requestMatchers(HttpMethod.GET, "/api/v1/books/{id}/comments").permitAll();
            request.requestMatchers(HttpMethod.POST, "/api/v1/books/{id}/comments").permitAll();
            request.anyRequest().authenticated();
        }).oauth2Login(Customizer.withDefaults()).build();
    }
}
