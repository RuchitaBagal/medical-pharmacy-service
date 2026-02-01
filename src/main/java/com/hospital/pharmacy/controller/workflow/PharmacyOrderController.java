package com.hospital.pharmacy.controller.workflow;

import com.hospital.pharmacy.service.workflow.PharmacyOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pharmacy")
@CrossOrigin(origins = "http://localhost:5173")
public class PharmacyOrderController {

    private final PharmacyOrderService service;

    public PharmacyOrderController(PharmacyOrderService service) {
        this.service = service;
    }

    @PostMapping("/process/{prescriptionId}")
    @PreAuthorize("hasRole('PHARMACIST')")
    public ResponseEntity<String> processPrescription(
            @PathVariable Long prescriptionId
    ) {
        service.processPrescription(prescriptionId, null);
        return ResponseEntity.ok("Prescription processed successfully");
    }
}
