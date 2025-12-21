package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public UserProfile createUser(@RequestBody UserProfile profile) {
        return userProfileService.createUser(profile);
    }

    @GetMapping("/{id}")
    public UserProfile getUser(@PathVariable Long id) {
        return userProfileService.getUserById(id);
    }

    @GetMapping
    public List<UserProfile> getAllUsers() {
        return userProfileService.getAllUsers();
    }

    @PutMapping("/{id}/status")
    public UserProfile updateStatus(@PathVariable Long id,
                                    @RequestParam boolean active) {
        return userProfileService.updateUserStatus(id, active);
    }

    @GetMapping("/lookup/{userId}")
    public UserProfile getByUserId(@PathVariable String userId) {
        return userProfileService.findByUserId(userId);
    }
}
