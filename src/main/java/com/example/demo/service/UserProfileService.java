package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository repo;
    private final PasswordEncoder encoder;

    // required order: (UserProfileRepository, PasswordEncoder)
    public UserProfileService(UserProfileRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public UserProfile createUser(UserProfile profile) {
        profile.setPassword(encoder.encode(profile.getPassword()));
        if (profile.getRole() == null) {
            profile.setRole("USER");
        }
        if (profile.getActive() == null) {
            profile.setActive(true);
        }
        return repo.save(profile);
    }

    public UserProfile getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserProfile findByUserId(String userId) {
        return repo.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<UserProfile> getAllUsers() {
        return repo.findAll();
    }

    public UserProfile updateUserStatus(Long id, boolean active) {
        UserProfile user = getUserById(id);
        user.setActive(active);
        return repo.save(user);
    }
}
