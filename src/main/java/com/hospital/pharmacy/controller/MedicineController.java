package com.hospital.pharmacy.controller;

import com.hospital.pharmacy.entity.Medicine;
import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.service.MedicineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService service;

    public MedicineController(MedicineService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Medicine medicine) {
        try {
            return ResponseEntity.ok(service.addMedicine(medicine));
        } catch (ApiException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public List<Medicine> getAll() {
        return service.getAllMedicines();
    }
}
