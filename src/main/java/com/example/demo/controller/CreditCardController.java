package com.example.demo.controller;

import com.example.demo.entity.CreditCardRecord;
import com.example.demo.service.CreditCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping
    public CreditCardRecord addCard(@RequestBody CreditCardRecord card) {
        return creditCardService.addCard(card);
    }

    @PutMapping("/{id}")
    public CreditCardRecord updateCard(@PathVariable Long id,
                                       @RequestBody CreditCardRecord card) {
        return creditCardService.updateCard(id, card);
    }

    @GetMapping("/user/{userId}")
    public List<CreditCardRecord> getCardsByUser(@PathVariable Long userId) {
        return creditCardService.getCardsByUser(userId);
    }

    @GetMapping("/{id}")
    public CreditCardRecord getCard(@PathVariable Long id) {
        return creditCardService.getCardById(id);
    }

    @GetMapping
    public List<CreditCardRecord> getAllCards() {
        return creditCardService.getAllCards();
    }
}
