package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {
    private final PurchaseIntentRecordRepository purchaseIntentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRecordRepository;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository purchaseIntentRepository,
            UserProfileRepository userProfileRepository,
            CreditCardRecordRepository creditCardRepository,
            RewardRuleRepository rewardRuleRepository,
            RecommendationRecordRepository recommendationRecordRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
        this.userProfileRepository = userProfileRepository;
        this.creditCardRepository = creditCardRepository;
        this.rewardRuleRepository = rewardRuleRepository;
        this.recommendationRecordRepository = recommendationRecordRepository;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long purchaseIntentId) {
        // Fetch intent
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(purchaseIntentId)
                .orElseThrow(() -> new BadRequestException("Intent not found"));
        
        // Fetch user
        UserProfile user = userProfileRepository.findById(intent.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found"));
        
        if (!user.getActive()) {
            throw new BadRequestException("User is not active");
        }
        
        // Get user's active cards
        List<CreditCardRecord> cards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
        if (cards.isEmpty()) {
            throw new BadRequestException("No active cards found for user");
        }
        
        // Find best card for this category
        CreditCardRecord bestCard = null;
        double maxReward = 0.0;
        
        for (CreditCardRecord card : cards) {
            List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            for (RewardRule rule : rules) {
                double reward = intent.getAmount() * rule.getMultiplier();
                if (reward > maxReward) {
                    maxReward = reward;
                    bestCard = card;
                }
            }
        }
        
        if (bestCard == null) {
            throw new BadRequestException("No suitable card found for category: " + intent.getCategory());
        }
        
        // Create recommendation
        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(intent.getUserId());
        rec.setPurchaseIntentId(purchaseIntentId);
        rec.setRecommendedCardId(bestCard.getId());
        rec.setExpectedRewardValue(maxReward);
        rec.setCalculationDetailsJson("{\"cardId\":" + bestCard.getId() + ",\"multiplier\":" + (maxReward/intent.getAmount()) + "}");
        rec.prePersist();
        
        return recommendationRecordRepository.save(rec);
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRecordRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRecordRepository.findAll();
    }
}
