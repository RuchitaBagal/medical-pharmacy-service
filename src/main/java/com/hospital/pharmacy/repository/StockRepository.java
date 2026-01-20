package com.hospital.pharmacy.repository;

import com.hospital.pharmacy.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByMedicine_MedicineId(Long medicineId);
}
