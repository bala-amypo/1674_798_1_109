package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepo;
    private final UserProfileRepository userRepo;
    private final CreditCardRecordRepository cardRepo;
    private final RewardRuleRepository ruleRepo;
    private final RecommendationRecordRepository recRepo;

    // required order: (PurchaseIntentRecordRepository, UserProfileRepository,
    //                  CreditCardRecordRepository, RewardRuleRepository,
    //                  RecommendationRecordRepository)
    public RecommendationEngineService(PurchaseIntentRecordRepository intentRepo,
                                       UserProfileRepository userRepo,
                                       CreditCardRecordRepository cardRepo,
                                       RewardRuleRepository ruleRepo,
                                       RecommendationRecordRepository recRepo) {
        this.intentRepo = intentRepo;
        this.userRepo = userRepo;
        this.cardRepo = cardRepo;
        this.ruleRepo = ruleRepo;
        this.recRepo = recRepo;
    }

    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = intentRepo.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        userRepo.findById(intent.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CreditCardRecord> activeCards =
                cardRepo.findActiveCardsByUser(intent.getUserId());

        double bestReward = -1;
        Long bestCardId = null;
        Map<Long, Double> cardRewards = new HashMap<>();

        for (CreditCardRecord card : activeCards) {
            List<RewardRule> rules =
                    ruleRepo.findActiveRulesForCardCategory(card.getId(),
                                                            intent.getCategory());
            double reward = 0;
            if (!rules.isEmpty()) {
                RewardRule r = rules.get(0);
                reward = intent.getAmount() * r.getMultiplier();
            }
            cardRewards.put(card.getId(), reward);
            if (reward > bestReward) {
                bestReward = reward;
                bestCardId = card.getId();
            }
        }

        if (bestCardId == null) {
            bestReward = 0;
        }

        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(intent.getUserId());
        rec.setPurchaseIntentId(intentId);
        rec.setRecommendedCardId(bestCardId);
        rec.setExpectedRewardValue(bestReward);
        rec.setCalculationDetailsJson(cardRewards.toString());

        return recRepo.save(rec);
    }

    public RecommendationRecord getRecommendationById(Long id) {
        return recRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recRepo.findByUserId(userId);
    }

    public List<RecommendationRecord> getAllRecommendations() {
        return recRepo.findAll();
    }
}
