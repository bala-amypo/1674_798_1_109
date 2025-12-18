package com.example.demo.service;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PurchaseIntentRecordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseIntentService {

    private final PurchaseIntentRecordRepository repo;

    // required order: (PurchaseIntentRecordRepository)
    public PurchaseIntentService(PurchaseIntentRecordRepository repo) {
        this.repo = repo;
    }

    public PurchaseIntentRecord createIntent(PurchaseIntentRecord intent) {
        if (intent.getAmount() == null || intent.getAmount() <= 0) {
            throw new BadRequestException("Amount must be > 0");
        }
        return repo.save(intent);
    }

    public List<PurchaseIntentRecord> getIntentsByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public PurchaseIntentRecord getIntentById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Intent not found"));
    }

    public List<PurchaseIntentRecord> getAllIntents() {
        return repo.findAll();
    }
}
