package com.core.service.ServiceImpl;

import java.util.List;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.core.domain.MinerGroupEntity;
import com.core.service.MinerGroupService;
import com.core.DAO.DAOImpl.MinerGroupDAO;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MinerGroupServiceImpl implements MinerGroupService {
    private final MinerGroupDAO MINER_GROUP_DAO;

    public MinerGroupServiceImpl(MinerGroupDAO minerGroupDAO) {
        this.MINER_GROUP_DAO = minerGroupDAO;
    }

    @Override
    public MinerGroupEntity findMinerGroupById(Long id) {
        return MINER_GROUP_DAO.findById(id).orElse(null);
    }

    @Override
    public List<MinerGroupEntity> findAllMinerGroups() {
        return MINER_GROUP_DAO.findAll().orElse(null);
    }

    @Override
    public MinerGroupEntity createMinerGroup(MinerGroupEntity minerGroup) {
        return MINER_GROUP_DAO.save(minerGroup).orElse(null);
    }

    @Override
    public MinerGroupEntity updateMinerGroup(MinerGroupEntity minerGroup) {
        return MINER_GROUP_DAO.update(minerGroup).orElse(null);
    }

    @Override
    public void deleteMinerGroup(Long id) {
        MINER_GROUP_DAO.delete(id);
    }
}
