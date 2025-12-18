package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@Tag(name = "Recommendations")
public class RecommendationController {

    private final RecommendationEngineService service;

    public RecommendationController(RecommendationEngineService service) {
        this.service = service;
    }

    @PostMapping("/generate/{intentId}")
    public ResponseEntity<RecommendationRecord> generate(@PathVariable Long intentId) {
        return ResponseEntity.ok(service.generateRecommendation(intentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationRecord>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getRecommendationsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRecommendationById(id));
    }

    @GetMapping
    public ResponseEntity<List<RecommendationRecord>> all() {
        return ResponseEntity.ok(service.getAllRecommendations());
    }
}
