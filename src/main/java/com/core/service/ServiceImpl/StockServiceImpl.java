package com.core.service.ServiceImpl;

import com.core.DAO.DAOImpl.StockDAO;
import com.core.domain.StockEntity;
import com.core.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StockServiceImpl implements StockService {
    public final StockDAO STOCK_DAO;
    @Autowired
    public StockServiceImpl(StockDAO stockDAO) {
        this.STOCK_DAO = stockDAO;
    }
    @Override
    public StockEntity findStockById(Long id) {
        return STOCK_DAO.findById(id).orElse(null);
    }

    @Override
    public List<StockEntity> findAllStockGroups() {
        return STOCK_DAO.findAll().orElse(null);
    }

    @Override
    public StockEntity createStockGroup(StockEntity stockEntity) {
        return STOCK_DAO.save(stockEntity).orElse(null);
    }

    @Override
    public StockEntity updateStockGroup(StockEntity stockEntity) {
        return STOCK_DAO.update(stockEntity).orElse(null);
    }

    @Override
    public void deleteStock(Long id) {
        STOCK_DAO.delete(id);
    }

    @Override
    public void saveAllStocks(List<StockEntity> stocks) {
        for (StockEntity stockEntity : stocks) {
            STOCK_DAO.save(stockEntity);
        }
    }

    @Override
    public Optional<StockEntity> checkIfStockExist(long id) {
        return STOCK_DAO.findById(id);
    }
}
