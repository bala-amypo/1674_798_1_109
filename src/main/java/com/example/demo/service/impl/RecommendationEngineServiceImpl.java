package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepository;
    private final UserProfileRepository userProfileRepository;
    private final CreditCardRecordRepository creditCardRepository;
    private final RewardRuleRepository rewardRuleRepository;
    private final RecommendationRecordRepository recommendationRepository;

    // ⚠️ Constructor order MUST match tests
    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository intentRepository,
            UserProfileRepository userProfileRepository,
            CreditCardRecordRepository creditCardRepository,
            RewardRuleRepository rewardRuleRepository,
            RecommendationRecordRepository recommendationRepository) {

        this.intentRepository = intentRepository;
        this.userProfileRepository = userProfileRepository;
        this.creditCardRepository = creditCardRepository;
        this.rewardRuleRepository = rewardRuleRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {

        PurchaseIntentRecord intent = intentRepository.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase intent not found"));

        Long userId = intent.getUserId();

        userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CreditCardRecord> activeCards =
                creditCardRepository.findActiveCardsByUser(userId);

        double maxReward = 0;
        CreditCardRecord bestCard = null;
        Map<String, Object> calculationDetails = new HashMap<>();

        for (CreditCardRecord card : activeCards) {

            List<RewardRule> rules =
                    rewardRuleRepository.findActiveRulesForCardCategory(
                            card.getId(), intent.getCategory());

            for (RewardRule rule : rules) {
                double reward = intent.getAmount() * rule.getMultiplier();

                calculationDetails.put(
                        "card_" + card.getId(),
                        reward
                );

                if (reward > maxReward) {
                    maxReward = reward;
                    bestCard = card;
                }
            }
        }

        RecommendationRecord record = new RecommendationRecord();
        record.setUserId(userId);
        record.setPurchaseIntentId(intent.getId());
        record.setRecommendedCardId(bestCard != null ? bestCard.getId() : null);
        record.setExpectedRewardValue(maxReward);
        record.setCalculationDetailsJson(calculationDetails.toString());

        return recommendationRepository.save(record);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recommendationRepository.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recommendationRepository.findAll();
    }
}
