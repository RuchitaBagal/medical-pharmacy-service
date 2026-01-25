package com.hospital.pharmacy.controller;

import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    // ✅ ADD stock
    @PostMapping("/{medicineId}")
    public ResponseEntity<?> addStock(@PathVariable Long medicineId,
                                      @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(service.addStock(medicineId, quantity));
        } catch (ApiException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // REDUCE stock
    @PutMapping("/reduce/{medicineId}")
    public ResponseEntity<?> reduceStock(@PathVariable Long medicineId,
                                         @RequestParam int quantity) {
        try {
            Map<String, Object> response = service.reduceStock(medicineId, quantity);
            return ResponseEntity.ok(response); //JSON returned ONLY on success
        } catch (ApiException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}