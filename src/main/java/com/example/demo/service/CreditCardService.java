// Service Interface
package com.example.demo.service;
import com.example.demo.entity.CreditCardRecord;
import java.util.List;
public interface CreditCardService {
    CreditCardRecord addCard(CreditCardRecord card);
    List<CreditCardRecord> getCardsByUser(Long userId);
    List<CreditCardRecord> getAllCards();
}

// Service Implementation
package com.example.demo.service.impl;
import com.example.demo.entity.CreditCardRecord;
import com.example.demo.repository.CreditCardRecordRepository;
import com.example.demo.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {
    private final CreditCardRecordRepository repository;
    @Override
    public CreditCardRecord addCard(CreditCardRecord card) { return repository.save(card); }
    @Override
    public List<CreditCardRecord> getCardsByUser(Long userId) { return repository.findByUserId(userId); }
    @Override
    public List<CreditCardRecord> getAllCards() { return repository.findAll(); }
}