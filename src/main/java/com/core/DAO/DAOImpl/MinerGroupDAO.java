package com.core.DAO.DAOImpl;

import com.core.DAO.DAOGeneric;
import com.core.config.HibernateUtil;
import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Transactional
@NoArgsConstructor
public class MinerGroupDAO implements DAOGeneric<MinerGroupEntity, Long> {
    private Logger log = LoggerFactory.getLogger(MinerDAO.class);

    private SessionFactory sessionFactory;


    public MinerGroupDAO(HibernateUtil hibernateUtil){
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    public MinerGroupDAO(Logger log, SessionFactory sessionFactory) {
        this.log = log;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<MinerGroupEntity> findById(Long minerGroupId) {
        Optional<MinerGroupEntity> minerGroup = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            minerGroup = Optional.ofNullable(session.get(MinerGroupEntity.class, minerGroupId));
        } catch (HibernateException e) {
            log.error("Error while finding Miner by ID: {}", minerGroupId);
        }

        if (minerGroup.isEmpty()) {
            log.error("The miner group with id {} didn't find!", minerGroupId);
        }

        return minerGroup;
    }

    @Override
    public Optional<List<MinerGroupEntity>> findAll() {
        Optional<List<MinerGroupEntity>> minerGroupList = Optional.empty();

        try (Session session = sessionFactory.openSession()) {
            Query<MinerGroupEntity> query = session.createQuery("FROM MinerGroupEntity", MinerGroupEntity.class);
            minerGroupList = Optional.ofNullable(query.getResultList());

        } catch (HibernateException e) {
            log.error("Error while finding all minerGroups", e);
        }

        return minerGroupList;
    }

    @Override
    public Optional<MinerGroupEntity> save(MinerGroupEntity minerGroup) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                MinerGroupEntity savedMinerGroup = session.merge(minerGroup);

                transaction.commit();

                return Optional.ofNullable(savedMinerGroup);
            } catch (PersistenceException e) {

                log.error("Error when saving minerGroup!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<MinerGroupEntity> update(MinerGroupEntity minerGroup) {
        Transaction transaction = null;
        MinerGroupEntity savedMiner = null;

        try (Session session = sessionFactory.openSession()) {
            try {

                transaction = session.beginTransaction();

                if (findById(minerGroup.getId()).isEmpty()) {
                    log.error("Miner with this id doesn't exists!");
                }

                savedMiner = session.merge(minerGroup);

                transaction.commit();
            } catch (PersistenceException e) {

                log.error("Error when saving minerGroup!", e);

            }
        } catch (HibernateException e) {
            log.error("Error when opening session", e);
        }

        return Optional.ofNullable(savedMiner);
    }

    @Override
    public void delete(Long minerGroupId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();

                MinerGroupEntity minerGroup = session.get(MinerGroupEntity.class, minerGroupId);

                if (minerGroup != null) {

                    if (minerGroup.getId() != 0) {
                        clearMinerGroupForMinerByMinerGroupId(minerGroup.getId());
                    }

                    session.remove(minerGroup);
                    transaction.commit();
                } else {
                    log.warn("Miner group with ID {} not found for deletion", minerGroupId);
                }

            } catch (HibernateException e) {

                log.error("Error when deleting minerGroup!", e);
            }
        } catch (HibernateException e) {
            log.error("Error when opening session!", e);
        }
    }

    public void clearMinerGroupForMinerByMinerGroupId(Long minerGroupId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();

            String sql = "UPDATE miner SET miner_group_id = null WHERE miner_group_id = :minerGroupId";

            session.createNativeQuery(sql, MinerEntity.class)
                    .setParameter("minerGroupId", minerGroupId)
                    .executeUpdate();

            transaction.commit();

        } catch (HibernateException e) {
            log.error("Failed to delete miner group from miner", e);
        }
    }
}
