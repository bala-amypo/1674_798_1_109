package com.example.demo.controller;

import com.example.demo.entity.PurchaseIntentRecord;
import com.example.demo.service.PurchaseIntentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/intents")
@Tag(name = "Intents")
public class PurchaseIntentController {

    private final PurchaseIntentService service;

    public PurchaseIntentController(PurchaseIntentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PurchaseIntentRecord> create(@RequestBody PurchaseIntentRecord intent) {
        return ResponseEntity.ok(service.createIntent(intent));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseIntentRecord>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getIntentsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseIntentRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getIntentById(id));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseIntentRecord>> all() {
        return ResponseEntity.ok(service.getAllIntents());
    }
}
