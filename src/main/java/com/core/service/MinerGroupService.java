package com.core.service;

import com.core.domain.MinerGroupEntity;

import java.util.List;
import java.util.Optional;

public interface MinerGroupService {
    MinerGroupEntity findMinerGroupById(Long id);
    List<MinerGroupEntity> findAllMinerGroups();
    MinerGroupEntity createMinerGroup(MinerGroupEntity minerGroup);
    MinerGroupEntity updateMinerGroup(MinerGroupEntity minerGroup);
    void deleteMinerGroup(Long id);
}
