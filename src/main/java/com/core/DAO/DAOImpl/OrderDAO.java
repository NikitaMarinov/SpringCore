package com.core.DAO.DAOImpl;

import com.core.DAO.DAOGeneric;
import com.core.config.HibernateUtil;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@Transactional
@NoArgsConstructor
public class OrderDAO implements DAOGeneric<OrderEntity, Long> {
    private SessionFactory sessionFactory;
    private Logger log = LoggerFactory.getLogger(MinerDAO.class);

    @Autowired
    public OrderDAO(HibernateUtil hibernateUtil){
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    public OrderDAO(Logger log, SessionFactory sessionFactory) {
        this.log = log;
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Optional<OrderEntity> findById(Long orderId) {
        Optional<OrderEntity> order = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            order = Optional.of(session.get(OrderEntity.class, orderId));
        } catch (HibernateException | NullPointerException e) {
            log.error("Error while finding Miner by ID: {}", orderId);
        }

        if(order.isEmpty()){
            log.error("The order with id {} didn't find!", orderId);
        }

        return order;
    }

    @Override
    public Optional<List<OrderEntity>> findAll() {
        Optional<List<OrderEntity>> orderList = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            Query<OrderEntity> query = session.createQuery("FROM OrderEntity", OrderEntity.class);
            orderList = Optional.ofNullable(query.getResultList());
        } catch (HibernateException e) {
            log.error("Error while finding all orders", e);
        }

        return orderList;
    }

    @Override
    public Optional<OrderEntity> save(OrderEntity order) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                OrderEntity savedOrder = session.merge(order);

                transaction.commit();

                return Optional.ofNullable(savedOrder);
            } catch (PersistenceException e) {

                log.error("Error when saving order!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrderEntity> update(OrderEntity order) {
        Transaction transaction = null;
        OrderEntity savedMiner = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                if(findById(order.getId()).isEmpty()){
                    log.error("Order with this id doesn't exists!");
                    throw new PersistenceException();
                }
                savedMiner = session.merge(order);

                transaction.commit();
            } catch (PersistenceException e) {

                log.error("Error when updating order!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }
        return Optional.ofNullable(savedMiner);
    }

    @Override
    public void delete(Long orderId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                OrderEntity order = session.get(OrderEntity.class, orderId);

                if (order != null) {

                    if(order.getId() != 0) {
                        clearOrderForMinerGroupByOrderId(order.getId());
                    }

                    session.remove(order);
                    transaction.commit();

                } else {
                    log.warn("Order with ID {} not found for deletion", orderId);
                }


            } catch (HibernateException e) {

                log.error("Error when deleting order!", e);

            }
        } catch (HibernateException e){
            log.error("Error when opening session!", e);
        }
    }
    public void clearOrderForMinerGroupByOrderId(Long orderId) {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            String sql = "UPDATE miner_group SET order_id = null WHERE order_id = :orderId";

            session.createNativeQuery(sql, MinerGroupEntity.class)
                    .setParameter("orderId", orderId)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            log.error("Failed to delete order from miner group", e);
        }
    }
}
