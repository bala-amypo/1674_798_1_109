package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intents")
public class PurchaseIntentController {

    private final PurchaseIntentService purchaseIntentService;

    public PurchaseIntentController(PurchaseIntentService purchaseIntentService) {
        this.purchaseIntentService = purchaseIntentService;
    }

    @PostMapping
    public PurchaseIntentRecord createIntent(@RequestBody PurchaseIntentRecord intent) {
        return purchaseIntentService.createIntent(intent);
    }

    @GetMapping("/user/{userId}")
    public List<PurchaseIntentRecord> getByUser(@PathVariable Long userId) {
        return purchaseIntentService.getIntentsByUser(userId);
    }

    @GetMapping("/{id}")
    public PurchaseIntentRecord getById(@PathVariable Long id) {
        return purchaseIntentService.getIntentById(id);
    }

    @GetMapping
    public List<PurchaseIntentRecord> getAll() {
        return purchaseIntentService.getAllIntents();
    }
}
