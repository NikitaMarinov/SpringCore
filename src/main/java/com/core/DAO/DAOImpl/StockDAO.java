package com.core.DAO.DAOImpl;

import com.core.DAO.DAOGeneric;
import com.core.config.HibernateUtil;
import com.core.domain.MinerGroupEntity;
import com.core.domain.StockEntity;
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
@NoArgsConstructor
public class StockDAO implements DAOGeneric<StockEntity, Long> {
    private  SessionFactory sessionFactory;
    private Logger log = LoggerFactory.getLogger(MinerDAO.class);

    @Autowired
    public StockDAO(HibernateUtil hibernateUtil){
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    public StockDAO(Logger log, SessionFactory sessionFactory) {
        this.log = log;
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Optional<StockEntity> findById(Long stockId) {
        Optional<StockEntity> stock = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            stock = Optional.ofNullable(session.get(StockEntity.class, stockId));
        } catch (HibernateException e) {
            log.error("Error while finding Stock by ID: {}", stockId);
        }

        if(stock.isEmpty()){
            log.error("The stock with id {} didn't find!", stockId );
        }

        return stock;
    }

    @Override
    public Optional<List<StockEntity>> findAll() {
        Optional<List<StockEntity>> stockList = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            Query<StockEntity> query = session.createQuery("FROM StockEntity", StockEntity.class);
            stockList = Optional.ofNullable(query.getResultList());
        } catch (HibernateException e) {
            log.error("Error while finding all stocks", e);
        }

        return stockList;
    }

    @Override
    public Optional<StockEntity> save(StockEntity stock) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                StockEntity savedStock = session.merge(stock);

                transaction.commit();

                return Optional.ofNullable(savedStock);
            } catch (PersistenceException e) {

                log.error("Error when saving stock!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<StockEntity> update(StockEntity stock) {
        Transaction transaction = null;
        StockEntity savedMiner = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                if(findById(stock.getId()).isEmpty()){
                    log.error("Miner with this id doesn't exists!");
                }

                savedMiner = session.merge(stock);

                transaction.commit();
            } catch (PersistenceException e) {

                log.error("Error when saving stock!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }
        return Optional.ofNullable(savedMiner);
    }

    @Override
    public void delete(Long stockId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                StockEntity stock = session.get(StockEntity.class, stockId);

                if (stock != null) {
                    if(stock.getId() != 0) {
                        clearStockForMinerGroupByStockId(stock.getId());
                    }
                    session.remove(stock);
                    transaction.commit();
                } else {
                    log.warn("Stock with ID {} not found for deletion", stockId);
                }

            } catch (HibernateException e) {

                log.error("Error when deleting stock!", e);
            }
        } catch (HibernateException e){
            log.error("Error when opening session!", e);
        }
    }

    public void clearStockForMinerGroupByStockId(Long stockId) {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            String sql = "UPDATE miner_group SET stock_id = null WHERE stock_id = :stockId";

            session.createNativeQuery(sql, MinerGroupEntity.class)
                    .setParameter("stockId", stockId)
                    .executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {

            log.error("Failed to delete stock from miner group", e);

        }
    }
}
