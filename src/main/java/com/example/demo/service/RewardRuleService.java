package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.RewardRuleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RewardRuleService {

    private final RewardRuleRepository repo;

    // required order: (RewardRuleRepository)
    public RewardRuleService(RewardRuleRepository repo) {
        this.repo = repo;
    }

    public RewardRule createRule(RewardRule rule) {
        if (rule.getMultiplier() == null || rule.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        return repo.save(rule);
    }

    public RewardRule updateRule(Long id, RewardRule updated) {
        RewardRule existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
        if (updated.getMultiplier() != null && updated.getMultiplier() <= 0) {
            throw new BadRequestException("Price multiplier must be > 0");
        }
        existing.setCategory(updated.getCategory());
        existing.setRewardType(updated.getRewardType());
        existing.setMultiplier(updated.getMultiplier());
        existing.setActive(updated.getActive());
        return repo.save(existing);
    }

    public List<RewardRule> getRulesByCard(Long cardId) {
        return repo.findByCardId(cardId);
    }

    public List<RewardRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    public List<RewardRule> getAllRules() {
        return repo.findAll();
    }
}
