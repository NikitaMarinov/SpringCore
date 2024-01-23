package com.core.service.ServiceImpl;

import com.core.DAO.DAOImpl.OrderDAO;
import com.core.domain.OrderEntity;
import com.core.service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDAO ORDER_DAO;
    
    public OrderServiceImpl(OrderDAO orderDAO){
        this.ORDER_DAO = orderDAO;
    } 
    @Override
    public OrderEntity findOrderById(Long id) {
        return ORDER_DAO.findById(id).orElse(null);
    }

    @Override
    public List<OrderEntity> findAllOrderGroups() {
        return ORDER_DAO.findAll().orElse(null);
    }

    @Override
    public OrderEntity createOrderGroup(OrderEntity orderEntity) {
            return ORDER_DAO.save(orderEntity).orElse(null);
    }

    @Override
    public OrderEntity updateOrderGroup(OrderEntity orderEntity) {
        return ORDER_DAO.update(orderEntity).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        ORDER_DAO.delete(id);
    }
}
