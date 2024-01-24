package com.core.service;



import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;

import java.util.Optional;

public interface ValidationService {
    Optional<MinerGroupEntity> createMinerGroupEntity(String[] minerGroupValues);

    Optional<MinerEntity> createMinerEntity(String[] minerValues);

    Optional<StockEntity> createStockEntity(String[] stockValues);

    Optional<OrderEntity> createOrderEntity(String[] orderValues);

}
