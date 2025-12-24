package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfile createUser(UserProfile userProfile) {
        if (userProfileRepository.existsByEmail(userProfile.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userProfileRepository.existsByUserId(userProfile.getUserId())) {
            throw new RuntimeException("UserId already exists");
        }
        userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userProfileRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userProfileRepository.findAll();
    }
}
