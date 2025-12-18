package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.JwtResponse;
import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserProfileService userProfileService;
    private final UserProfileRepository userRepo;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    // required order:
    // (UserProfileService, UserProfileRepository, AuthenticationManager, JwtUtil)
    public AuthService(UserProfileService userProfileService,
                       UserProfileRepository userRepo,
                       AuthenticationManager authManager,
                       JwtUtil jwtUtil) {
        this.userProfileService = userProfileService;
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public JwtResponse register(RegisterRequest req) {
        if (userRepo.existsByUserId(req.getUserId())) {
            // message text required by spec
            throw new BadRequestException("Event code already exists");
        }
        UserProfile profile = new UserProfile();
        profile.setUserId(req.getUserId());
        profile.setFullName(req.getFullName());
        profile.setEmail(req.getEmail());
        profile.setPassword(req.getPassword());
        profile.setRole(req.getRole() == null ? "USER" : req.getRole());
        profile.setActive(true);
        userProfileService.createUser(profile);
        String token = jwtUtil.generateToken(profile.getUserId());
        return new JwtResponse(token);
    }

    public JwtResponse login(LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUserId(), req.getPassword()));
        if (!auth.isAuthenticated()) {
            throw new BadRequestException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(req.getUserId());
        return new JwtResponse(token);
    }
}
