package com.core.service.ServiceImpl;

import com.core.DAO.DAOImpl.MinerDAO;
import com.core.domain.MinerEntity;
import com.core.service.MinerService;

import java.util.List;

public class MinerServiceImpl implements MinerService {
    private final MinerDAO MINER_DAO;

    public MinerServiceImpl(MinerDAO minerDAO) {
        this.MINER_DAO = minerDAO;
    }
    @Override
    public MinerEntity findMinerById(Long id) {
        return MINER_DAO.findById(id).orElse(null);
    }

    @Override
    public List<MinerEntity> findAllMinerGroups() {
        return MINER_DAO.findAll().orElse(null);
    }

    @Override
    public MinerEntity createMinerGroup(MinerEntity minerEntity) {
        return MINER_DAO.save(minerEntity).orElse(null);
    }

    @Override
    public MinerEntity updateMinerGroup(MinerEntity minerEntity) {
        return MINER_DAO.update(minerEntity).orElse(null);
    }

    @Override
    public void deleteMiner(Long id) {
        MINER_DAO.delete(id);
    }
}
