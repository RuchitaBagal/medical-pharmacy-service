package com.hospital.pharmacy.service.impl;

import com.hospital.pharmacy.entity.Medicine;
import com.hospital.pharmacy.entity.Stock;
import com.hospital.pharmacy.exception.ApiException;
import com.hospital.pharmacy.repository.MedicineRepository;
import com.hospital.pharmacy.repository.StockRepository;
import com.hospital.pharmacy.service.StockService;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final MedicineRepository medicineRepository;

    public StockServiceImpl(StockRepository stockRepository,
                            MedicineRepository medicineRepository) {
        this.stockRepository = stockRepository;
        this.medicineRepository = medicineRepository;
    }

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

    @Override
    public void reduceStock(Long medicineId, int quantity) {

        Stock stock = stockRepository.findByMedicine_MedicineId(medicineId)
                .orElseThrow(() -> new ApiException("No stock found for this medicine"));

        if (stock.getQuantity() <= 0) {
            throw new ApiException("No remaining stock available");
        }

        if (stock.getQuantity() < quantity) {
            throw new ApiException("Insufficient stock available");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }
}
