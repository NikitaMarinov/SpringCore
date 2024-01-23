package com.core.service;

import com.core.domain.OrderEntity;

import java.util.List;

public interface OrderService {
    OrderEntity findOrderById(Long id);
    List<OrderEntity> findAllOrderGroups();
    OrderEntity createOrderGroup(OrderEntity orderGroup);
    OrderEntity updateOrderGroup(OrderEntity orderGroup);
    void deleteOrder(Long id);
}
