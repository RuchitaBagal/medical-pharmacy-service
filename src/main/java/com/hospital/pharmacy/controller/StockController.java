package com.hospital.pharmacy.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.service.StockService;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    // ADD stock
    @PostMapping("/{medicineId}")
    @PreAuthorize("hasRole('PHARMACIST')")
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
    @PreAuthorize("hasRole('PHARMACIST')")
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