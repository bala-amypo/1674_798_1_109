package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_record")
public class RecommendationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    
    private Long purchaseIntentId;
    
    private Long recommendedCardId;
    
    private Double expectedRewardValue = 0.0;
    
    private String calculationDetailsJson;
    
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getPurchaseIntentId() { return purchaseIntentId; }
    public void setPurchaseIntentId(Long purchaseIntentId) { this.purchaseIntentId = purchaseIntentId; }
    
    public Long getRecommendedCardId() { return recommendedCardId; }
    public void setRecommendedCardId(Long recommendedCardId) { this.recommendedCardId = recommendedCardId; }
    
    public Double getExpectedRewardValue() { 
        return expectedRewardValue != null && expectedRewardValue >= 0 ? expectedRewardValue : 0.0; 
    }
    public void setExpectedRewardValue(Double expectedRewardValue) { 
        this.expectedRewardValue = (expectedRewardValue != null && expectedRewardValue >= 0) ? expectedRewardValue : 0.0; 
    }
    
    public String getCalculationDetailsJson() { return calculationDetailsJson; }
    public void setCalculationDetailsJson(String calculationDetailsJson) { this.calculationDetailsJson = calculationDetailsJson; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
