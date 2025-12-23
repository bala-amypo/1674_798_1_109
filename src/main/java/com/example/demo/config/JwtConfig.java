package com.example.demo.config;

import com.example.demo.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        String secret = "my-secret-key-my-secret-key-my-secret-key";
        Long expirationMs = 86400000L; // 1 day
        return new JwtUtil(secret.getBytes(), expirationMs);
    }
}
