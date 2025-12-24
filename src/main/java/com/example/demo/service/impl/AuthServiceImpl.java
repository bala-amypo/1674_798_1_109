package com.example.demo.service.impl;

import com.example.demo.dto.JwtResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthServiceImpl {
    
    private final UserProfileService userService;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthServiceImpl(UserProfileService userService,
                          UserProfileRepository userProfileRepository,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.userProfileRepository = userProfileRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<JwtResponse> register(RegisterRequest request) {
        UserProfile user = new UserProfile();
        user.setUserId("U" + System.currentTimeMillis());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        
        UserProfile savedUser = userService.createUser(user);
        String token = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
        
        JwtResponse response = new JwtResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), token);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<JwtResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserProfile user = userProfileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        JwtResponse response = new JwtResponse(user.getId(), user.getEmail(), user.getRole(), token);
        return ResponseEntity.ok(response);
    }
}
