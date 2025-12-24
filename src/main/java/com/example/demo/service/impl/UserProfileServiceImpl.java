package com.example.demo.service.impl;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfile createUser(UserProfile profile) {
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return repository.save(profile);
    }

    @Override
    public UserProfile getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<UserProfile> getAllUsers() {
        return repository.findAll();
    }
}