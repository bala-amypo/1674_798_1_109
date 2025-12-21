// src/main/java/com/example/demo/service/impl/PurchaseIntentServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import com.example.demo.service.PurchaseIntentService;

import java.util.List;

public class PurchaseIntentServiceImpl implements PurchaseIntentService {

    private final PurchaseIntentRecordRepository intentRepo;

    public PurchaseIntentServiceImpl(PurchaseIntentRecordRepository intentRepo) {
        this.intentRepo = intentRepo;
    }

    @Override
    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {
        if (intent.getAmount() == null || intent.getAmount() <= 0) {
            throw new BadRequestException("Amount must be > 0");
        }
        return intentRepo.save(intent);
    }

    @Override
    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return intentRepo.findByUserId(userId);
    }

    @Override
    public PurchaseIntentRecord getIntentById(Long id) {
        return intentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
    }

    @Override
    public List<PurchaseIntentRecord> getAllIntents() {
        return intentRepo.findAll();
    }
}
