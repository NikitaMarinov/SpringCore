package com.core.service;

import com.core.domain.OrderEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    OrderEntity findOrderById(Long id);
    List<OrderEntity> findAllOrderGroups();
    OrderEntity createOrderGroup(OrderEntity orderGroup);
    OrderEntity updateOrderGroup(OrderEntity orderGroup);
    void deleteOrder(Long id);
    Optional<OrderEntity> checkIfOrderExist(long id);

    void saveAllOrders(List<OrderEntity> ordersList);
}
