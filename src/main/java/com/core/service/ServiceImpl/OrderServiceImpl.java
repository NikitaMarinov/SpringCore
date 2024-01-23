package com.core.service.ServiceImpl;

import com.core.DAO.DAOImpl.OrderDAO;
import com.core.domain.OrderEntity;
import com.core.service.OrderService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO){
        this.orderDAO = orderDAO;
    } 
    @Override
    public OrderEntity findOrderById(Long id) {
        return orderDAO.findById(id).orElse(null);
    }

    @Override
    public List<OrderEntity> findAllOrderGroups() {
        return orderDAO.findAll().orElse(null);
    }

    @Override
    public OrderEntity createOrderGroup(OrderEntity orderEntity) {
            return orderDAO.save(orderEntity).orElse(null);
    }

    @Override
    public OrderEntity updateOrderGroup(OrderEntity orderEntity) {
        return orderDAO.update(orderEntity).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderDAO.delete(id);
    }
}
