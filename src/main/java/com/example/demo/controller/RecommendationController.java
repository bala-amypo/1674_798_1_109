package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService recommendationService;

    public RecommendationController(RecommendationEngineService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/generate/{intentId}")
    public RecommendationRecord generate(@PathVariable Long intentId) {
        return recommendationService.generateRecommendation(intentId);
    }

    @GetMapping("/user/{userId}")
    public List<RecommendationRecord> getByUser(@PathVariable Long userId) {
        return recommendationService.getRecommendationsByUser(userId);
    }

    @GetMapping("/{id}")
    public RecommendationRecord getById(@PathVariable Long id) {
        return recommendationService.getRecommendationById(id);
    }

    @GetMapping
    public List<RecommendationRecord> getAll() {
        return recommendationService.getAllRecommendations();
    }
}
