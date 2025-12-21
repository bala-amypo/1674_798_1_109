// src/main/java/com/example/demo/controller/CreditCardController.java
package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {

    private final CreditCardService cardService;

    public CreditCardController(CreditCardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CreditCardRecord> add(@RequestBody CreditCardRecord card) {
        return ResponseEntity.ok(cardService.addCard(card));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditCardRecord> update(@PathVariable Long id,
                                                   @RequestBody CreditCardRecord updated) {
        return ResponseEntity.ok(cardService.updateCard(id, updated));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CreditCardRecord>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getCardsByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardRecord> get(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @GetMapping
    public ResponseEntity<List<CreditCardRecord>> getAll() {
        return ResponseEntity.ok(cardService.getAllCards());
    }
}
