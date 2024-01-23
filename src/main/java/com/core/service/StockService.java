package com.core.service;

import com.core.domain.StockEntity;

import java.util.List;

public interface StockService {
    StockEntity findStockById(Long id);
    List<StockEntity> findAllStockGroups();
    StockEntity createStockGroup(StockEntity stockGroup);
    StockEntity updateStockGroup(StockEntity stockGroup);
    void deleteStock(Long id);
}
