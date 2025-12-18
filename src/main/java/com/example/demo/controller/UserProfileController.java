package com.example.demo.controller;

import com.example.demo.entity.UserProfile;
import com.example.demo.service.UserProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserProfile> create(@RequestBody UserProfile p) {
        return ResponseEntity.ok(service.createUser(p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> all() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserProfile> status(@PathVariable Long id,
                                              @RequestParam boolean active) {
        return ResponseEntity.ok(service.updateUserStatus(id, active));
    }

    @GetMapping("/lookup/{userId}")
    public ResponseEntity<UserProfile> lookup(@PathVariable String userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }
}
