package com.example.demo.service;

import com.example.demo.entity.RecommendationRecord;
import com.example.demo.exception.BadRequestException;

import java.util.List;

public interface RecommendationEngineService {
    RecommendationRecord generateRecommendation(Long purchaseIntentId) throws BadRequestException;
    List<RecommendationRecord> getRecommendationsByUser(Long userId);
    List<RecommendationRecord> getAllRecommendations();
}
