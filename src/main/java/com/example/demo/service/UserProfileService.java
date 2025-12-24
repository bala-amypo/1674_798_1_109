package com.example.demo.service;
import com.example.demo.entity.UserProfile;
import java.util.List;

public interface UserProfileService {
    UserProfile createUser(UserProfile profile);
    UserProfile getUserById(Long id);
    List<UserProfile> getAllUsers();
}