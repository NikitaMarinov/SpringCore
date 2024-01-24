package com.core.aspects;

import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;
import com.core.domain.enums.MinerStatusEnum;
import com.core.domain.enums.OrderStatusEnum;
import com.core.domain.enums.TypeOfOreEnum;
import com.core.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@Slf4j
public class ValidationAspect {
    private final ValidationService VALID_SERVICE;

    @Autowired
    public ValidationAspect(ValidationService VALID_SERVICE) {
        this.VALID_SERVICE = VALID_SERVICE;
    }

    @Around("execution(* com.core.service.ServiceImpl.ValidationServiceImpl.createMinerGroupEntity(..))")
    public Optional<MinerGroupEntity> validateMinerGroup(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String[] minerGroupValues = (String[]) args[0];

        Optional<Long> minerGroupId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(minerGroupValues, 0), "Miner group Id");
        Optional<String> name = VALID_SERVICE.validateStringWithRegex(VALID_SERVICE.getCSVValue(minerGroupValues, 1), "Miner group name");
        Optional<Integer> productivity = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(minerGroupValues, 2), "Miner group productivity");
        Optional<Long> orderId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(minerGroupValues, 4), "Miner group orderId");
        Optional<Long> stockId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(minerGroupValues, 5), "Miner group stockId");
        Optional<TypeOfOreEnum> mineOre = VALID_SERVICE.parseEnumValue(VALID_SERVICE.getCSVValue(minerGroupValues, 3), TypeOfOreEnum.class);

        if (minerGroupId.isEmpty() || name.isEmpty() || productivity.isEmpty() ||
                orderId.isEmpty() || stockId.isEmpty() || mineOre.isEmpty()) {
            return Optional.empty();
        }

        Object[] changedByAspectArgs = {minerGroupId.get(), name.get(), productivity.get(), mineOre.get(), orderId.get(), stockId.get()};
        Object[] oneParameterArgsArray = {changedByAspectArgs};

        return (Optional<MinerGroupEntity>) joinPoint.proceed(oneParameterArgsArray);
    }

    @Around("@annotation(com.core.annotations.ValidMiner)")
    public Optional<MinerEntity> validateMiner(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String[] minerValues = (String[]) args[0];

        Optional<Long> minerId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(minerValues, 0), "Miner Id");
        Optional<String> name = VALID_SERVICE.validateStringWithRegex(VALID_SERVICE.getCSVValue(minerValues, 1), "Miner name");
        Optional<String> surname = VALID_SERVICE.validateStringWithRegex(VALID_SERVICE.getCSVValue(minerValues, 2), "Miner surname");
        Optional<Integer> salary = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(minerValues, 3), "Miner salary");
        Optional<Boolean> atWork = VALID_SERVICE.parseBooleanValue(VALID_SERVICE.getCSVValue(minerValues, 4));
        Optional<MinerStatusEnum> mineStatus = VALID_SERVICE.parseEnumValue(VALID_SERVICE.getCSVValue(minerValues, 5), MinerStatusEnum.class);
        Optional<Long> minerGroupId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(minerValues, 6), "Miner group id");

        if (minerId.isEmpty() || name.isEmpty() || surname.isEmpty() || salary.isEmpty() ||
                minerGroupId.isEmpty() || mineStatus.isEmpty() || atWork.isEmpty()) {
            return Optional.empty();
        }

        Object[] changedByAspectArgs = {minerId.get(), name.get(), surname.get(), salary.get(), atWork.get(), mineStatus.get(), minerGroupId.get()};
        Object[] oneParameterArgsArray = {changedByAspectArgs};

        return (Optional<MinerEntity>) joinPoint.proceed(oneParameterArgsArray);

    }

    @Around("execution(* com.core.service.ServiceImpl.ValidationServiceImpl.createStockEntity(..))")
    public Optional<StockEntity> validateStock(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String[] stockValues = (String[]) args[0];

        Optional<Long> stockId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(stockValues, 0), "Stock ID");
        Optional<Integer> coal = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(stockValues, 1), "Stock coal");
        Optional<Integer> iron = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(stockValues, 2), "Stock iron");
        Optional<Integer> gold = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(stockValues, 3), "Stock gold");

        if (coal.isEmpty() || iron.isEmpty() || gold.isEmpty() || stockId.isEmpty()) {
            return Optional.empty();
        }

        Object[] changedByAspectArgs = {stockId.get(), coal.get(), iron.get(), gold.get()};
        Object[] oneParameterArgsArray = {changedByAspectArgs};

        return (Optional<StockEntity>) joinPoint.proceed(oneParameterArgsArray);
    }

    @Around("execution(* com.core.service.ServiceImpl.ValidationServiceImpl.createOrderEntity(..))")
    public Optional<OrderEntity> validateOrder(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String[] orderValues = (String[]) args[0];

        Optional<Long> orderId = VALID_SERVICE.validateLong(VALID_SERVICE.getCSVValue(orderValues, 0), "Order ID");
        Optional<TypeOfOreEnum> typeOfOre = VALID_SERVICE.parseEnumValue(VALID_SERVICE.getCSVValue(orderValues, 1), TypeOfOreEnum.class);
        Optional<Integer> quantity = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(orderValues, 2), "Order quantity");
        Optional<Integer> price = VALID_SERVICE.validateInt(VALID_SERVICE.getCSVValue(orderValues, 3), "Order price");
        Optional<OrderStatusEnum> orderStatus = VALID_SERVICE.parseEnumValue(VALID_SERVICE.getCSVValue(orderValues, 4), OrderStatusEnum.class);

        if (orderId.isEmpty() || quantity.isEmpty() || price.isEmpty() || typeOfOre.isEmpty() || orderStatus.isEmpty()) {
            return Optional.empty();
        }

        Object[] changedByAspectArgs = {orderId.get(), typeOfOre.get(), quantity.get(), price.get(), orderStatus.get()};
        Object[] oneParameterArgsArray = {changedByAspectArgs};

        return (Optional<OrderEntity>) joinPoint.proceed(oneParameterArgsArray);
    }

}
