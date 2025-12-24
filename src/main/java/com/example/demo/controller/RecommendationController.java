package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
    
    private final RecommendationEngineService recommendationService;

    @Autowired
    public RecommendationController(RecommendationEngineService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate/{purchaseIntentId}")
    public ResponseEntity<RecommendationRecord> generateRecommendation(@PathVariable Long purchaseIntentId) {
        return ResponseEntity.ok(recommendationService.generateRecommendation(purchaseIntentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationRecord>> getRecommendationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<RecommendationRecord>> getAllRecommendations() {
        return ResponseEntity.ok(recommendationService.getAllRecommendations());
    }
}
