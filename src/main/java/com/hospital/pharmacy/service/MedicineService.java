package com.hospital.pharmacy.service;

import com.hospital.pharmacy.entity.Medicine;
import java.util.List;

public interface MedicineService {

    Medicine addMedicine(Medicine medicine);
    List<Medicine> getAllMedicines();
    Medicine getMedicineById(Long id);
}
