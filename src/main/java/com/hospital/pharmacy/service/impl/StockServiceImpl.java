package com.hospital.pharmacy.service.impl;

import com.hospital.pharmacy.entity.Medicine;
import com.hospital.pharmacy.entity.Stock;
import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.repository.MedicineRepository;
import com.hospital.pharmacy.repository.StockRepository;
import com.hospital.pharmacy.service.StockService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final MedicineRepository medicineRepository;

    public StockServiceImpl(StockRepository stockRepository,
                            MedicineRepository medicineRepository) {
        this.stockRepository = stockRepository;
        this.medicineRepository = medicineRepository;
    }

    //@PreAuthorize("hasRole('PHARMACIST')")
    @Override
    public Stock addStock(Long medicineId, int quantity) {

        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ApiException("No such medicine exists"));

        Stock stock = stockRepository.findByMedicine_MedicineId(medicineId)
                .orElse(new Stock());

        stock.setMedicine(medicine);
        stock.setQuantity(stock.getQuantity() + quantity);

        return stockRepository.save(stock);
    }

    // UPDATED METHOD
    //@PreAuthorize("hasRole('PHARMACIST')")
    @Override
    public Map<String, Object> reduceStock(Long medicineId, int quantity) {

        Stock stock = stockRepository.findByMedicine_MedicineId(medicineId)
                .orElseThrow(() -> new ApiException("No stock found for this medicine"));

        if (stock.getQuantity() <= 0) {
            throw new ApiException("No remaining stock available");
        }

        if (stock.getQuantity() < quantity) {
            throw new ApiException("Insufficient stock available");
        }

        // reduce stock
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);

        Medicine medicine = stock.getMedicine();

        
        Map<String, Object> res = new HashMap<>();
        res.put("name", medicine.getName());
        res.put("price", medicine.getPrice());
        res.put("quantity", quantity);

        return res;
    }
}