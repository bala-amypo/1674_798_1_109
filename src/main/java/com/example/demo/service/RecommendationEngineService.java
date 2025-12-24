package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import java.util.List;

public interface RecommendationEngineService {
    RecommendationRecord generateRecommendation(Long purchaseIntentId);
    List<RecommendationRecord> getRecommendationsByUser(Long userId);
    List<RecommendationRecord> getAllRecommendations();
}
