// src/main/java/com/example/demo/repository/RewardRuleRepository.java
package com.example.demo.repository;

import com.example.demo.entity.RewardRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardRuleRepository extends JpaRepository<RewardRule, Long> {

    @Query("select r from RewardRule r where r.cardId = :cardId and r.category = :category and r.active = true")
    List<RewardRule> findActiveRulesForCardCategory(Long cardId, String category);

    List<RewardRule> findByActiveTrue();
}
