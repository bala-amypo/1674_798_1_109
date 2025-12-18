package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@Tag(name = "Cards")
public class CreditCardController {

    private final CreditCardService service;

    public CreditCardController(CreditCardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CreditCardRecord> add(@RequestBody CreditCardRecord card) {
        return ResponseEntity.ok(service.addCard(card));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardRecord> update(@PathVariable Long id,
                                                   @RequestBody CreditCardRecord card) {
        return ResponseEntity.ok(service.updateCard(id, card));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardRecord>> byUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getCardsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getCardById(id));
    }

    @GetMapping
    public ResponseEntity<List<CreditCardRecord>> all() {
        return ResponseEntity.ok(service.getAllCards());
    }
}
