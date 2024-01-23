package com.core.DAO.DAOImpl;

import com.core.DAO.DAOGeneric;
import com.core.config.HibernateUtil;
import com.core.domain.MinerEntity;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Transient;
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
public class MinerDAO implements DAOGeneric<MinerEntity, Long> {
    private Logger log = LoggerFactory.getLogger(MinerDAO.class);
    private SessionFactory sessionFactory;

    @Autowired
    public MinerDAO(HibernateUtil hibernateUtil) {
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    public MinerDAO(Logger log, SessionFactory sessionFactory) {
        this.log = log;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<MinerEntity> findById(Long minerId) {
        Optional<MinerEntity> miner = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            miner = Optional.ofNullable(session.get(MinerEntity.class, minerId));
        } catch (HibernateException e) {
            log.error("Error while finding Miner by ID: {}", minerId);
        }

        if (miner.isEmpty()) {
            log.error("The miner with id {} didn't find!", minerId);
        }

        return miner;
    }

    @Override
    public Optional<List<MinerEntity>> findAll() {
        Optional<List<MinerEntity>> minerList = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            Query<MinerEntity> query = session.createQuery("FROM MinerEntity", MinerEntity.class);
            minerList = Optional.ofNullable(query.getResultList());
        } catch (HibernateException e) {
            log.error("Error while finding all miners", e);
        }

        return minerList;
    }

    @Override
    public Optional<MinerEntity> save(MinerEntity miner) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                MinerEntity savedMiner = session.merge(miner);

                transaction.commit();

                return Optional.ofNullable(savedMiner);
            } catch (PersistenceException e) {
                log.error("Error when saving miner!", e);
            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<MinerEntity> update(MinerEntity miner) {
        Transaction transaction = null;
        MinerEntity savedMiner = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                if (findById(miner.getId()).isEmpty()) {
                    log.error("Miner with this id doesn't exists!");
                }
                savedMiner = session.merge(miner);

                transaction.commit();
            } catch (PersistenceException e) {
                log.error("Error when saving miner!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }

        return Optional.ofNullable(savedMiner);
    }

    @Override
    public void delete(Long minerId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                MinerEntity miner = session.get(MinerEntity.class, minerId);

                if (miner != null) {
                    session.remove(miner);
                    transaction.commit();
                    log.info("Miner with ID " + minerId + " was deleted!");
                } else {
                    log.warn("Miner with ID {} not found for deletion", minerId);
                }

            } catch (HibernateException e) {
                log.error("Error when deleting miner!", e);
            }
        } catch (HibernateException e) {
            log.error("Error when opening session!", e);
        }
    }
}

