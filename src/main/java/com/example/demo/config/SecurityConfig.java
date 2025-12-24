package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF for simple REST/JWT setup
            .csrf(csrf -> csrf.disable())

            // Set authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow health/servlet endpoints without auth
                .requestMatchers("/status", "/status/**", "/simple-status", "/simple-status/**").permitAll()
                // Allow auth endpoints (register/login) without auth
                .requestMatchers("/api/auth/**").permitAll()
                // You can also open H2 console or others if needed:
                // .requestMatchers("/h2-console/**").permitAll()
                // Everything else allowed (for this lab); change to authenticated() when you add real JWT filter
                .anyRequest().permitAll()
            )

            // Disable default login form & HTTP Basic to avoid that blue login page
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // Stateless sessions (good with JWT; harmless for now)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
