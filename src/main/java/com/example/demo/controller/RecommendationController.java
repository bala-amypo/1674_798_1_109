// src/main/java/com/example/demo/controller/RecommendationController.java
package com.example.demo.controller;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationEngineService recService;

    public RecommendationController(RecommendationEngineService recService) {
        this.recService = recService;
    }

    @PostMapping("/generate/{intentId}")
    public ResponseEntity<RecommendationRecord> generate(@PathVariable Long intentId) {
        return ResponseEntity.ok(recService.generateRecommendation(intentId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationRecord>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recService.getRecommendationsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(recService.getRecommendationById(id));
    }

    @GetMapping
    public ResponseEntity<List<RecommendationRecord>> getAll() {
        return ResponseEntity.ok(recService.getAllRecommendations());
    }
}
