package com.remoteAccess.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; enable as needed
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Permit all requests (adjust as needed)
                        .anyRequest().authenticated()       // Require authentication for other requests
                )
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("script-src 'self'; style-src 'self';") // Set Content Security Policy
                        )
                );

        return http.build();
    }
}
