package com.core.service.ServiceImpl;

import com.core.annotations.ValidMiner;
import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;
import com.core.domain.enums.MinerStatusEnum;
import com.core.domain.enums.OrderStatusEnum;
import com.core.domain.enums.TypeOfOreEnum;
import com.core.service.MinerGroupService;
import com.core.service.OrderService;
import com.core.service.StockService;
import com.core.service.ValidationService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@NoArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {
    private MinerGroupService MINER_GROUP_SERVICE;
    private StockService STOCK_SERVICE;
    private OrderService ORDER_SERVICE;
    private  final int MAX_VALUE = 1000000000;
    private  final int MIN_VALUE = 0;

    @Autowired
    public ValidationServiceImpl(OrderService orderService, MinerGroupService minerGroupService, StockService stockService) {
        ORDER_SERVICE = orderService;
        MINER_GROUP_SERVICE = minerGroupService;
        STOCK_SERVICE = stockService;
    }

    @ValidMiner
    @Override
    public  Optional<MinerEntity> createMinerEntity(Object[] minerValues){


        Optional<MinerGroupEntity> minerGroup = MINER_GROUP_SERVICE.checkIfMinerGroupExist((Long) minerValues[6]);
        if (minerGroup.isEmpty()) {
            log.error("The Stock with ID " +  minerValues[5] + " doesn't exist, we can't add Miner Group");

            return Optional.empty();
        }

        return Optional.of(new MinerEntity()
                .setId((Long) minerValues[0])
                .setName((String) minerValues[1])
                .setSurname((String) minerValues[2])
                .setSalary((Integer)minerValues[3])
                .setAtWork((Boolean) minerValues[4])
                .setStatus((MinerStatusEnum) minerValues[5])
                .setMinerGroup(minerGroup.get()));
    }

    @Override
    public  Optional<MinerGroupEntity>  createMinerGroupEntity(Object[] minerGroupValues) {
        Optional<StockEntity> stock = STOCK_SERVICE.checkIfStockExist((Long) minerGroupValues[5]);
        if(stock.isEmpty()) {
            log.error("The Stock with ID" + minerGroupValues[5] + " doesn't exist, we can't add Miner Group");
            return Optional.empty();
        }
        Optional<OrderEntity> order = ORDER_SERVICE.checkIfOrderExist((Long) minerGroupValues[4]);
        if(order.isEmpty()) {
            log.error("The Order with ID" + minerGroupValues[5] + " doesn't exist, we can't add Miner Group");

            return Optional.empty();
        }


        return Optional.of(new MinerGroupEntity()
                .setId((Long) minerGroupValues[0])
                .setName((String) minerGroupValues[1])
                .setProductivity((Integer) minerGroupValues[2])
                .setMineOre((TypeOfOreEnum) minerGroupValues[3])
                .setStock(stock.get())
                .setOrder(order.get()));
    }
    @Override
    public  Optional<StockEntity> createStockEntity(Object[] stockValues) {

        return Optional.of(new StockEntity()
                .setId((Long) stockValues[0])
                .setCoal((Integer) stockValues[1])
                .setIron((Integer) stockValues[2])
                .setGold((Integer) stockValues[3]));
    }
    @Override
    public  Optional<OrderEntity> createOrderEntity(Object[] orderValues) {


        return Optional.of(new OrderEntity()
                .setId((Long) orderValues[0])
                .setTypeOfOre((TypeOfOreEnum) orderValues[1])
                .setQuantity((Integer) orderValues[2])
                .setPrice((Integer) orderValues[3])
                .setOrderStatus((OrderStatusEnum) orderValues[4]));
    }
    @Override
    public  String getCSVValue(String[] values, int index) {
        return values[index].trim();
    }
    @Override
    public  Optional<Long> validateLong(String value, String fieldName) {
        try {
            long longValue = Long.parseLong(value);
            if (longValue < MIN_VALUE || longValue > MAX_VALUE) {
                log.error("Invalid {} value: {}", fieldName, longValue);

                return Optional.empty();
            }
            return Optional.of(longValue);
        } catch (NumberFormatException e) {
            log.error("Error parsing {} value: {}", fieldName, value);

            return Optional.empty();
        }
    }

    @Override
    public  Optional<Integer> validateInt(String value, String fieldName) {
        try {
            int intValue = Integer.parseInt(value);
            if (intValue < MIN_VALUE || intValue > MAX_VALUE) {
                log.error("Invalid {} value: {}", fieldName, intValue);

                return Optional.empty();
            }

            return Optional.of(intValue);
        } catch (NumberFormatException e) {
            log.error("Error parsing {} value: {}", fieldName, value);

            return Optional.empty();
        }
    }

    @Override
    public  Optional<String> validateStringWithRegex(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            log.error("{} should not be empty or null", fieldName);

            return Optional.empty();
        }

        final int MIN_LENGTH = 2;
        final int MAX_LENGTH = 32;

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            log.error("{} length should be between {} and {}", fieldName, MIN_LENGTH, MAX_LENGTH);

            return Optional.empty();
        }

        final String REGEX = "^[a-zA-Z ]+$";
        if (!value.matches(REGEX)) {
            log.error("{} contains invalid characters: {} ", fieldName, value);

            return Optional.empty();
        }

        return Optional.of(value.trim());
    }

    @Override
    public <T extends Enum<T>> Optional<T> parseEnumValue(String value, Class<T> enumType) {
        try {
            return Optional.of(Enum.valueOf(enumType, value));
        } catch (IllegalArgumentException e) {
            log.error("Error parsing enum value in miner: {}", e.getMessage());

            return Optional.empty();
        }
    }

    @Override
    public Optional<Boolean> parseBooleanValue(String value) {
        try {
            return Optional.of(Boolean.parseBoolean(value));
        } catch (IllegalArgumentException e) {
            log.error("Error parsing boolean value in miner: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
