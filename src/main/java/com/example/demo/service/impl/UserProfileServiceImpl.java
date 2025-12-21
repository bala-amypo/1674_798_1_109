// src/main/java/com/example/demo/service/impl/UserProfileServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserProfileServiceImpl(UserProfileRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserProfile createUser(UserProfile profile) {
        if (userRepo.existsByUserId(profile.getUserId()) || userRepo.existsByEmail(profile.getEmail())) {
            throw new BadRequestException("Duplicate userId or email");
        }
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        if (profile.getActive() == null) {
            profile.setActive(true);
        }
        return userRepo.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserProfile findByUserId(String userId) {
        Optional<UserProfile> user = userRepo.findAll().stream()
                .filter(u -> userId.equals(u.getUserId()))
                .findFirst();
        return user.orElseThrow(() -> new ResourceNotFoundException("User not found with userId: " + userId));
    }

    @Override
    public UserProfile findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile user = getUserById(id);
        user.setActive(active);
        return userRepo.save(user);
    }
}
