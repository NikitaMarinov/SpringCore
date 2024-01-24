package com.core.service.ServiceImpl;

import com.core.DAO.DAOImpl.MinerDAO;
import com.core.domain.MinerEntity;
import com.core.service.MinerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class MinerServiceImpl implements MinerService {
    private final MinerDAO MINER_DAO;
    @Autowired
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
    public MinerEntity createMiner(MinerEntity minerEntity) {
        return MINER_DAO.save(minerEntity).orElse(null);
    }

    @Override
    public MinerEntity updateMiner(MinerEntity minerEntity) {
        return MINER_DAO.update(minerEntity).orElse(null);
    }
    @Override
    public void deleteMiner(Long id) {
        MINER_DAO.delete(id);
    }

    @Override
    public void saveAllMiners(List<MinerEntity> miners) {
        for(MinerEntity miner : miners){
            MINER_DAO.save(miner);
        }
    }
}
