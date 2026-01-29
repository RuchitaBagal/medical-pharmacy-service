package com.hospital.pharmacy.service.workflow.impl;


import com.hospital.pharmacy.dto.PrescriptionMedicineDTO;
import com.hospital.pharmacy.dto.PrescriptionResponseDTO;
import com.hospital.pharmacy.entity.Medicine;
import com.hospital.pharmacy.entity.Stock;
import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.feign.PrescriptionFeignClient;
import com.hospital.pharmacy.repository.MedicineRepository;
import com.hospital.pharmacy.repository.StockRepository;
import com.hospital.pharmacy.service.workflow.PharmacyOrderService;
import org.springframework.stereotype.Service;

@Service
public class PharmacyOrderServiceImpl implements PharmacyOrderService {

    private final PrescriptionFeignClient prescriptionClient;
    private final MedicineRepository medicineRepository;
    private final StockRepository stockRepository;

    public PharmacyOrderServiceImpl(
            PrescriptionFeignClient prescriptionClient,
            MedicineRepository medicineRepository,
            StockRepository stockRepository
    ) {
        this.prescriptionClient = prescriptionClient;
        this.medicineRepository = medicineRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public void processPrescription(Long prescriptionId, String token) {

        PrescriptionResponseDTO prescription =
                prescriptionClient.getPrescription(prescriptionId);

        if (prescription.getMedicines() == null ||
            prescription.getMedicines().isEmpty()) {
            throw new ApiException("No medicines found in prescription");
        }

        
        System.out.println("MEDICINES FROM PRESCRIPTION = " + prescription.getMedicines());

        for (PrescriptionMedicineDTO med : prescription.getMedicines()) {

            // Find medicine by name
            Medicine medicine = medicineRepository
                    .findByNameIgnoreCase(med.getMedicineName())
                    .orElseThrow(() ->
                            new ApiException("Medicine not found: " + med.getMedicineName())
                    );

            // Find stock by medicine_id
            Stock stock = stockRepository
                    .findByMedicine_MedicineId(medicine.getMedicineId())
                    .orElseThrow(() ->
                            new ApiException("Stock not found for medicine: " + medicine.getName())
                    );

            // Check stock availability
            if (stock.getQuantity() < med.getQuantity()) {
                throw new ApiException("Insufficient stock for: " + medicine.getName());
            }

            // Reduce stock
            stock.setQuantity(stock.getQuantity() - med.getQuantity());

            // Save stock
            stockRepository.save(stock);
        }
    }

    
    
}