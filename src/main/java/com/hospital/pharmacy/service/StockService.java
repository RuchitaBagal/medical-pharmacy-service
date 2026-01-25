package com.hospital.pharmacy.service;

import java.util.Map;

import com.hospital.pharmacy.entity.Stock;

public interface StockService {

    Stock addStock(Long medicineId, int quantity);

    Map<String, Object> reduceStock(Long medicineId, int quantity);
}
