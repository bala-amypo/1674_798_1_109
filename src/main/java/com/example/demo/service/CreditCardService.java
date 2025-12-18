package com.example.demo.service;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CreditCardRecordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreditCardService {

    private final CreditCardRecordRepository repo;

    // required order: (CreditCardRecordRepository)
    public CreditCardService(CreditCardRecordRepository repo) {
        this.repo = repo;
    }

    public CreditCardRecord addCard(CreditCardRecord card) {
        if (card.getAnnualFee() == null || card.getAnnualFee() < 0) {
            card.setAnnualFee(0.0);
        }
        return repo.save(card);
    }

    public CreditCardRecord updateCard(Long id, CreditCardRecord updated) {
        CreditCardRecord existing = getCardById(id);
        existing.setCardName(updated.getCardName());
        existing.setIssuer(updated.getIssuer());
        existing.setCardType(updated.getCardType());
        existing.setAnnualFee(updated.getAnnualFee());
        existing.setStatus(updated.getStatus());
        return repo.save(existing);
    }

    public List<CreditCardRecord> getCardsByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public CreditCardRecord getCardById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));
    }

    public List<CreditCardRecord> getAllCards() {
        return repo.findAll();
    }
}
