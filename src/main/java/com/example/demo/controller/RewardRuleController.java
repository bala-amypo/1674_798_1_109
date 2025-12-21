package com.example.demo.controller;

import com.example.demo.entity.RewardRule;
import com.example.demo.service.RewardRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reward-rules")
public class RewardRuleController {

    private final RewardRuleService rewardRuleService;

    public RewardRuleController(RewardRuleService rewardRuleService) {
        this.rewardRuleService = rewardRuleService;
    }

    @PostMapping
    public RewardRule createRule(@RequestBody RewardRule rule) {
        return rewardRuleService.createRule(rule);
    }

    @PutMapping("/{id}")
    public RewardRule updateRule(@PathVariable Long id,
                                 @RequestBody RewardRule rule) {
        return rewardRuleService.updateRule(id, rule);
    }

    @GetMapping("/card/{cardId}")
    public List<RewardRule> getRulesByCard(@PathVariable Long cardId) {
        return rewardRuleService.getRulesByCard(cardId);
    }

    @GetMapping("/active")
    public List<RewardRule> getActiveRules() {
        return rewardRuleService.getActiveRules();
    }

    @GetMapping
    public List<RewardRule> getAllRules() {
        return rewardRuleService.getAllRules();
    }
}
