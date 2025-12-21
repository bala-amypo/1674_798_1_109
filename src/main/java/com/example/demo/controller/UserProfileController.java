// src/main/java/com/example/demo/controller/UserProfileController.java
package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService userService;

    public UserProfileController(UserProfileService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile profile) {
        return ResponseEntity.ok(userService.createUser(profile));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> get(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserProfile> updateStatus(@PathVariable Long id,
                                                    @RequestParam boolean active) {
        return ResponseEntity.ok(userService.updateUserStatus(id, active));
    }

    @GetMapping("/lookup/{userId}")
    public ResponseEntity<UserProfile> getByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(userService.findByUserId(userId));
    }
}
