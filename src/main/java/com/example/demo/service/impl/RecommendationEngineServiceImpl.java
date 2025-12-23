package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.RecommendationEngineService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationEngineServiceImpl implements RecommendationEngineService {

    private final PurchaseIntentRecordRepository intentRepo;
    private final UserProfileRepository userRepo;
    private final CreditCardRecordRepository cardRepo;
    private final RewardRuleRepository ruleRepo;
    private final RecommendationRecordRepository recRepo;

    public RecommendationEngineServiceImpl(
            PurchaseIntentRecordRepository intentRepo,
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

    @Override
    public RecommendationRecord generateRecommendation(Long intentId) {
        PurchaseIntentRecord intent = intentRepo.findById(intentId)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));

        Long userId = intent.getUserId();
        userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<CreditCardRecord> cards = cardRepo.findActiveCardsByUser(userId);
        if (cards.isEmpty()) {
            throw new ResourceNotFoundException("No active cards");
        }

        CreditCardRecord bestCard = null;
        double bestReward = -1;

        for (CreditCardRecord card : cards) {
            List<RewardRule> rules =
                    ruleRepo.findActiveRulesForCardCategory(card.getId(), intent.getCategory());
            double reward = rules.stream()
                    .map(RewardRule::getMultiplier)
                    .max(Comparator.naturalOrder())
                    .orElse(0.0) * intent.getAmount();

            if (reward > bestReward) {
                bestReward = reward;
                bestCard = card;
            }
        }

        RecommendationRecord rec = new RecommendationRecord();
        rec.setUserId(userId);
        rec.setPurchaseIntentId(intentId);
        rec.setRecommendedCardId(bestCard.getId());
        rec.setExpectedRewardValue(bestReward);
        rec.setCalculationDetailsJson("{\"intentId\":" + intentId + "}");

        return recRepo.save(rec);
    }

    @Override
    public RecommendationRecord getRecommendationById(Long id) {
        return recRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found"));
    }

    @Override
    public List<RecommendationRecord> getRecommendationsByUser(Long userId) {
        return recRepo.findByUserId(userId);
    }

    @Override
    public List<RecommendationRecord> getAllRecommendations() {
        return recRepo.findAll();
    }
}
