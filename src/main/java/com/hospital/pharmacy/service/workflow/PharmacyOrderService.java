package com.hospital.pharmacy.service.workflow;

import com.hospital.pharmacy.dto.PrescriptionMedicineDTO;
import com.hospital.pharmacy.dto.PrescriptionResponseDTO;
import com.hospital.pharmacy.entity.Medicine;
import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.feign.PrescriptionFeignClient;
import com.hospital.pharmacy.repository.MedicineRepository;
import com.hospital.pharmacy.service.StockService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface PharmacyOrderService {

	void processPrescription(Long prescriptionId, String token);
    
}