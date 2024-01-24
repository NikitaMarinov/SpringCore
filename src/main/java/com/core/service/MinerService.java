package com.core.service;

import com.core.domain.MinerEntity;

import java.util.List;

public interface MinerService {
    MinerEntity findMinerById(Long id);
    List<MinerEntity> findAllMinerGroups();
    MinerEntity createMinerGroup(MinerEntity minerGroup);
    MinerEntity updateMinerGroup(MinerEntity minerGroup);
    void deleteMiner(Long id);
    void saveAllMiners(List<MinerEntity> miners);

}
