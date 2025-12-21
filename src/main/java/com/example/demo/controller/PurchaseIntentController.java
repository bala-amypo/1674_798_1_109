// src/main/java/com/example/demo/controller/PurchaseIntentController.java
package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/intents")
public class PurchaseIntentController {

    private final PurchaseIntentService intentService;

    public PurchaseIntentController(PurchaseIntentService intentService) {
        this.intentService = intentService;
    }

    @PostMapping
    public ResponseEntity<PurchaseIntentRecord> create(@RequestBody PurchaseIntentRecord intent) {
        return ResponseEntity.ok(intentService.createIntent(intent));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseIntentRecord>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(intentService.getIntentsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseIntentRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(intentService.getIntentById(id));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseIntentRecord>> getAll() {
        return ResponseEntity.ok(intentService.getAllIntents());
    }
}
