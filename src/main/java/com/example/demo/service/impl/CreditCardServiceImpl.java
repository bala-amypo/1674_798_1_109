package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.repository.CreditCardRecordRepository;  // Correct import
import com.example.demo.service.CreditCardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRecordRepository creditCardRepository;  // Correct type

    public CreditCardServiceImpl(CreditCardRecordRepository creditCardRepository) {  // Fixed parameter
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCardRecord addCard(CreditCardRecord card) {
        // JPA @PrePersist handles timestamps automatically
        return creditCardRepository.save(card);
    }

    @Override
    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return creditCardRepository.findByUserId(userId);
    }

    @Override
    public List<CreditCardRecord> getAllCards() {
        return creditCardRepository.findAll();
    }
}
