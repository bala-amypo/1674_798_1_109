package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;

import java.util.List;

public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRecordRepository purchaseIntentRepository;

    // ⚠️ Constructor order MUST match tests
    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository purchaseIntentRepository) {
        this.purchaseIntentRepository = purchaseIntentRepository;
    }

    @Override
    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {

        if (intent.getAmount() == null || intent.getAmount() <= 0) {
            throw new BadRequestException("Amount must be greater than 0");
        }

        return purchaseIntentRepository.save(intent);
    }

    @Override
    public PurchaseIntentRecord getIntentById(Long id) {
        return purchaseIntentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase intent not found"));
    }

    @Override
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return purchaseIntentRepository.findByUserId(userId);
    }

    @Override
    public List<PurchaseIntentRecord> getAllIntents() {
        return purchaseIntentRepository.findAll();
    }
}
