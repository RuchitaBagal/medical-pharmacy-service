package com.hospital.pharmacy.service.impl;

import com.hospital.pharmacy.entity.Medicine;
import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.repository.MedicineRepository;
import com.hospital.pharmacy.service.MedicineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

    public MedicineServiceImpl(MedicineRepository repository) {
        this.repository = repository;
    }

    @Override
    public Medicine addMedicine(Medicine medicine) {

        repository.findByNameIgnoreCase(medicine.getName())
                .ifPresent(m -> {
                    throw new ApiException(
                            "Medicine already exists: " + medicine.getName()
                    );
                });

        return repository.save(medicine);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    @Override
    public Medicine getMedicineById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
    }
}
