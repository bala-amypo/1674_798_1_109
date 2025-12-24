// src/main/java/com/example/demo/entity/RecommendationRecord.java
package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
public class RecommendationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;             // added-by-you: tests check relation
    private Long purchaseIntentId;   // added-by-you
    private Long recommendedCardId;
    private Double expectedRewardValue;
    @Column(columnDefinition = "TEXT")
    private String calculationDetailsJson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RecommendationRecord() {
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPurchaseIntentId() { return purchaseIntentId; }
    public void setPurchaseIntentId(Long purchaseIntentId) { this.purchaseIntentId = purchaseIntentId; }

    public Long getRecommendedCardId() { return recommendedCardId; }
    public void setRecommendedCardId(Long recommendedCardId) { this.recommendedCardId = recommendedCardId; }

    public Double getExpectedRewardValue() { return expectedRewardValue; }
    public void setExpectedRewardValue(Double expectedRewardValue) { this.expectedRewardValue = expectedRewardValue; }

    public String getCalculationDetailsJson() { return calculationDetailsJson; }
    public void setCalculationDetailsJson(String calculationDetailsJson) { this.calculationDetailsJson = calculationDetailsJson; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // added-by-you: must be PUBLIC if called directly
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.updatedAt == null) {
            this.updatedAt = LocalDateTime.now();
        }
        if (this.expectedRewardValue == null || this.expectedRewardValue < 0.0) {
            this.expectedRewardValue = 0.0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
