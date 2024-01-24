package com.core.service;


import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;

import java.util.Optional;

public interface ValidationService {
    Optional<MinerGroupEntity> createMinerGroupEntity(Object[] minerGroupValues);

    Optional<MinerEntity> createMinerEntity(Object[] minerValues);

    Optional<StockEntity> createStockEntity(Object[] stockValues);

    Optional<OrderEntity> createOrderEntity(Object[] orderValues);

    Optional<Boolean> parseBooleanValue(String value);

    <T extends Enum<T>> Optional<T> parseEnumValue(String value, Class<T> enumType);

    Optional<String> validateStringWithRegex(String value, String fieldName);

    Optional<Integer> validateInt(String value, String fieldName);

    String getCSVValue(String[] values, int index);

    Optional<Long> validateLong(String value, String fieldName);
}
