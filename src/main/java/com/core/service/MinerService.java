package com.core.service;

import com.core.domain.MinerEntity;

import java.util.List;

public interface MinerService {
    MinerEntity findMinerById(Long id);
    List<MinerEntity> findAllMinerGroups();
    MinerEntity createMiner(MinerEntity minerGroup);
    MinerEntity updateMiner(MinerEntity minerGroup);
    void deleteMiner(Long id);
    void saveAllMiners(List<MinerEntity> miners);

}
