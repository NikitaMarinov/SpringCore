package com.core.service.ServiceImpl;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class ValidationServiceImpl implements ValidationService {
    private final MinerGroupService MINER_GROUP_SERVICE;
    private final StockService STOCK_SERVICE;
    private final OrderService ORDER_SERVICE;
    private  final int MAX_VALUE = 1000000000;
    private  final int MIN_VALUE = 0;

    @Autowired
    public ValidationServiceImpl(OrderService orderService, MinerGroupService minerGroupService, StockService stockService) {
        ORDER_SERVICE = orderService;
        MINER_GROUP_SERVICE = minerGroupService;
        STOCK_SERVICE = stockService;
    }

    public  Optional<MinerEntity> createMinerEntity(String[] minerValues){
        Optional<Long> minerId = validateLong(getCSVValue(minerValues, 0), "Miner Id");
        Optional<String> name = validateStringWithRegex(getCSVValue(minerValues, 1), "Miner name");
        Optional<String> surname = validateStringWithRegex(getCSVValue(minerValues, 2), "Miner surname");
        Optional<Integer> salary = validateInt(getCSVValue(minerValues, 3), "Miner salary");
        Optional<Long> minerGroupId = validateLong(getCSVValue(minerValues, 6), "Miner group id");
        Optional<Boolean> atWork = parseBooleanValue(getCSVValue(minerValues, 4));
        Optional<MinerStatusEnum> mineStatus = parseEnumValue(getCSVValue(minerValues, 5), MinerStatusEnum.class);

        if (minerId.isEmpty() || name.isEmpty() || surname.isEmpty() || salary.isEmpty() ||
                minerGroupId.isEmpty() || mineStatus.isEmpty() || atWork.isEmpty()) {
            return Optional.empty();
        }

        Optional<MinerGroupEntity> minerGroup = MINER_GROUP_SERVICE.checkIfMinerGroupExist(minerGroupId.get());
        if (minerGroup.isEmpty()) {
            log.error("The Stock with ID " + minerGroupId + " doesn't exist, we can't add Miner Group");

            return Optional.empty();
        }

        return Optional.of(new MinerEntity()
                .setId(minerId.get())
                .setName(name.get())
                .setSurname(surname.get())
                .setSalary(salary.get())
                .setAtWork(atWork.get())
                .setStatus(mineStatus.get())
                .setMinerGroup(minerGroup.get()));
    }


    public  Optional<MinerGroupEntity> createMinerGroupEntity(String[] minerGroupValues) {
        Optional<Long> minerGroupId = validateLong(getCSVValue(minerGroupValues, 0), "Miner group Id");
        Optional<String> name = validateStringWithRegex(getCSVValue(minerGroupValues, 1), "Miner group name");
        Optional<Integer> productivity = validateInt(getCSVValue(minerGroupValues, 2), "Miner group productivity");
        Optional<Long> orderId = validateLong(getCSVValue(minerGroupValues, 4), "Miner group orderId");
        Optional<Long> stockId = validateLong(getCSVValue(minerGroupValues, 5), "Miner group stockId");
        Optional<TypeOfOreEnum> mineOre = parseEnumValue(getCSVValue(minerGroupValues,3),TypeOfOreEnum.class);

        if (minerGroupId.isEmpty() || name.isEmpty() || productivity.isEmpty() ||
                orderId.isEmpty() || stockId.isEmpty() || mineOre.isEmpty()) {
            return Optional.empty();
        }

        Optional<StockEntity> stock = STOCK_SERVICE.checkIfStockExist(stockId.get());
        if(stock.isEmpty()) {
            log.error("The Stock with ID" + stockId.get() + " doesn't exist, we can't add Miner Group");

            return Optional.empty();
        }
        Optional<OrderEntity> order = ORDER_SERVICE.checkIfOrderExist(orderId.get());
        if(order.isEmpty()) {
            log.error("The Order with ID" + orderId.get() + " doesn't exist, we can't add Miner Group");

            return Optional.empty();
        }


        return Optional.of(new MinerGroupEntity()
                .setId(minerGroupId.get())
                .setName(name.get())
                .setProductivity(productivity.get())
                .setMineOre(mineOre.get())
                .setStock(stock.get())
                .setOrder(order.get()));
    }

    public  Optional<StockEntity> createStockEntity(String[] stockValues) {
        Optional<Long> stockId = validateLong(getCSVValue(stockValues, 0), "Stock ID");
        Optional<Integer> coal = validateInt(getCSVValue(stockValues, 1), "Stock coal");
        Optional<Integer> iron = validateInt(getCSVValue(stockValues, 2), "Stock iron");
        Optional<Integer> gold = validateInt(getCSVValue(stockValues, 3), "Stock gold");

        if (coal.isEmpty() || iron.isEmpty() || gold.isEmpty() || stockId.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new StockEntity()
                .setId(stockId.get())
                .setCoal(coal.get())
                .setIron(iron.get())
                .setGold(gold.get()));
    }

    public  Optional<OrderEntity> createOrderEntity(String[] orderValues) {
        Optional<Long> orderId = validateLong(getCSVValue(orderValues, 0), "Order ID");
        Optional<TypeOfOreEnum> typeOfOre = parseEnumValue(getCSVValue(orderValues, 1),TypeOfOreEnum.class);
        Optional<Integer> quantity = validateInt(getCSVValue(orderValues, 2), "Order quantity");
        Optional<Integer> price = validateInt(getCSVValue(orderValues, 3), "Order price");
        Optional<OrderStatusEnum> orderStatus = parseEnumValue(getCSVValue(orderValues, 4), OrderStatusEnum.class);

        if (orderId.isEmpty() || quantity.isEmpty() || price.isEmpty() || typeOfOre.isEmpty() || orderStatus.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new OrderEntity()
                .setId(orderId.orElseThrow())
                .setTypeOfOre(typeOfOre.get())
                .setQuantity(quantity.orElseThrow())
                .setPrice(price.orElseThrow())
                .setOrderStatus(orderStatus.get()));
    }

    private  String getCSVValue(String[] values, int index) {
        return values[index].trim();
    }

    private  Optional<Long> validateLong(String value, String fieldName) {
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

     private  Optional<Integer> validateInt(String value, String fieldName) {
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


    private  Optional<String> validateStringWithRegex(String value, String fieldName) {
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


    private <T extends Enum<T>> Optional<T> parseEnumValue(String value, Class<T> enumType) {
        try {
            return Optional.of(Enum.valueOf(enumType, value));
        } catch (IllegalArgumentException e) {
            log.error("Error parsing enum value in miner: {}", e.getMessage());

            return Optional.empty();
        }
    }

    private Optional<Boolean> parseBooleanValue(String value) {
        try {
            return Optional.of(Boolean.parseBoolean(value));
        } catch (IllegalArgumentException e) {
            log.error("Error parsing boolean value in miner: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
