package com.hospital.pharmacy.service;

import com.hospital.pharmacy.entity.Stock;

public interface StockService {

    Stock addStock(Long medicineId, int quantity);

    void reduceStock(Long medicineId, int quantity);
}
