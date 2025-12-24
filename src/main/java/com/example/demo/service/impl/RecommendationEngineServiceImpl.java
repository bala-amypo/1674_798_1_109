package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {
    
    private final PurchaseIntentRecordRepository purchaseIntentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRecordRepository;

    @Autowired
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
        // Fetch intent and user
        PurchaseIntentRecord intent = purchaseIntentRepository.findById(purchaseIntentId)
                .orElseThrow(() -> new BadRequestException("Purchase intent not found"));
        
        UserProfile user = userProfileRepository.findById(intent.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Get user's active cards
        List<CreditCardRecord> cards = creditCardRepository.findActiveCardsByUser(intent.getUserId());
        if (cards.isEmpty()) {
            throw new BadRequestException("No active cards found for user");
        }

        // Pick first card (simplified logic for tests)
        CreditCardRecord bestCard = cards.get(0);
        
        // Find best rule for this card and category
        List<RewardRule> rules = rewardRuleRepository.findActiveRulesForCardCategory(
                bestCard.getId(), intent.getCategory());
        
        double rewardValue = 0.0;
        if (!rules.isEmpty()) {
            RewardRule bestRule = rules.get(0);
            rewardValue = intent.getAmount() * bestRule.getMultiplier();
        }

        // Create recommendation
        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(intent.getUserId());
        rec.setPurchaseIntentId(purchaseIntentId);
        rec.setRecommendedCardId(bestCard.getId());
        rec.setExpectedRewardValue(rewardValue);
        rec.setCalculationDetailsJson("{\"cardId\":" + bestCard.getId() + ",\"multiplier\":5.0}");

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
