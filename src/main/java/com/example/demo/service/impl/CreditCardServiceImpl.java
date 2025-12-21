package com.example.demo.service.impl;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;

import java.util.List;

public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRecordRepository creditCardRepository;

    // ⚠️ Constructor order MUST match tests
    public CreditCardServiceImpl(CreditCardRecordRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCardRecord addCard(CreditCardRecord card) {

        if (card.getAnnualFee() != null && card.getAnnualFee() < 0) {
            throw new BadRequestException("Annual fee must be >= 0");
        }

        return creditCardRepository.save(card);
    }

    @Override
    public CreditCardRecord updateCard(Long id, CreditCardRecord updated) {

        CreditCardRecord existing = creditCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        if (updated.getAnnualFee() != null && updated.getAnnualFee() < 0) {
            throw new BadRequestException("Annual fee must be >= 0");
        }

        existing.setCardName(updated.getCardName());
        existing.setIssuer(updated.getIssuer());
        existing.setCardType(updated.getCardType());
        existing.setAnnualFee(updated.getAnnualFee());
        existing.setStatus(updated.getStatus());

        return creditCardRepository.save(existing);
    }

    @Override
    public CreditCardRecord getCardById(Long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
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
