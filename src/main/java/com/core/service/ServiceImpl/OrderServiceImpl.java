package com.core.service.ServiceImpl;

import com.core.DAO.DAOImpl.OrderDAO;
import com.core.domain.OrderEntity;
import com.core.service.OrderService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Data
@NoArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    @Autowired
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

    @Override
    public Optional<OrderEntity> checkIfOrderExist(long id) {
        return orderDAO.findById(id);
    }

    @Override
    public void saveAllOrders(List<OrderEntity> ordersList) {
        for(OrderEntity orderEntity : ordersList) {
            orderDAO.save(orderEntity);
        }
    }
}

