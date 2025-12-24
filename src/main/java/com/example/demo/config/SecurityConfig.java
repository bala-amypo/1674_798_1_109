package com.example.demo.config;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // ==============================
    // Auth / UserDetails
    // ==============================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserProfileRepository userProfileRepository) {
        return email -> {
            UserProfile user = userProfileRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserProfileRepository userProfileRepository,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(userProfileRepository));
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ==============================
    // HTTP security (fixes login page)
    // ==============================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // No CSRF for simple REST/JWT apps
                .csrf(csrf -> csrf.disable())

                // Use our AuthenticationProvider
                .authenticationProvider(authenticationProvider(null, passwordEncoder()))

                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Allow auth endpoints freely
                        .requestMatchers("/api/auth/**").permitAll()
                        // Allow status/health endpoints freely (your servlet)
                        .requestMatchers("/status", "/status/**", "/simple-status", "/simple-status/**").permitAll()
                        // For this lab: allow everything else too
                        .anyRequest().permitAll()
                )

                // Disable default login form & HTTP Basic so the blue page disappears
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // Stateless (good for JWT; harmless for now)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
