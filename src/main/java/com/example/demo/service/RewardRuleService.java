package com.example.demo.service;

import com.example.demo.entity.RewardRule;
import java.util.List;

public interface RewardRuleService {
    RewardRule createRule(RewardRule rule);
    List<RewardRule> getActiveRules();
    List<RewardRule> getAllRules();
}
