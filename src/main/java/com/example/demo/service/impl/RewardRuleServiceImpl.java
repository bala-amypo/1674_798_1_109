package com.example.demo.service.impl;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RewardRuleRepository;
import com.example.demo.service.RewardRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardRuleServiceImpl implements RewardRuleService {

    private final RewardRuleRepository rewardRepo;

    public RewardRuleServiceImpl(RewardRuleRepository rewardRepo) {
        this.rewardRepo = rewardRepo;
    }

    @Override
    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() == null || rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return rewardRepo.save(rule);
    }

    @Override
    public RewardRule updateRule(Long id, RewardRule updated) {
        RewardRule existing = rewardRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
        existing.setCategory(updated.getCategory());
        existing.setRewardType(updated.getRewardType());
        existing.setMultiplier(updated.getMultiplier());
        existing.setActive(updated.getActive());
        existing.setCardId(updated.getCardId());
        return rewardRepo.save(existing);
    }

    @Override
    public List<RewardRule> getRulesByCard(Long cardId) {
        return rewardRepo.findAll().stream()
                .filter(r -> cardId.equals(r.getCardId()))
                .toList();
    }

    @Override
    public List<RewardRule> getActiveRules() {
        return rewardRepo.findByActiveTrue();
    }

    @Override
    public List<RewardRule> getAllRules() {
        return rewardRepo.findAll();
    }
}
