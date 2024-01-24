package com.core.service.ServiceImpl;

import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;
import com.core.service.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CsvReadeServiceImpl implements CsvReadeService {

    private final OrderService ORDER_SERVICE;
    private final MinerGroupService MINER_GROUP_SERVICE;
    private final StockService STOCK_SERVICE;
    private final MinerService MINER_SERVICE;
    private final ValidationService VALIDATION_SERVICE;

    @Autowired
    public CsvReadeServiceImpl(OrderServiceImpl orderService, MinerGroupServiceImpl minerGroupService,
                               StockServiceImpl stockService, MinerServiceImpl minerService,
                               ValidationService validationService) {
        ORDER_SERVICE = orderService;
        MINER_GROUP_SERVICE = minerGroupService;
        STOCK_SERVICE = stockService;
        MINER_SERVICE = minerService;
        VALIDATION_SERVICE = validationService;
    }

    @Override
    @Transactional
    public String loadData() {
        loadOrderData();
        loadStockData();
        loadMinerGroupData();
        loadMinerData();

        return "SUCCESSFUL";
    }


    @Override
    @Transactional
    public void loadStockData() {
        String csvFile = "src/main/resources/static/stock.csv";
        List<StockEntity> stocks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] stockValues = line.split(",");

                Optional<StockEntity> stock = VALIDATION_SERVICE.createStockEntity(stockValues);
                if (stock.isEmpty()) {
                    continue;
                }

                stocks.add(stock.get());
            }
        } catch (IOException e) {
            log.error("Error while loading data!");
        }

        STOCK_SERVICE.saveAllStocks(stocks);
    }



    @Override
    @Transactional
    public void loadOrderData() {
        String csvFile = "src/main/resources/static/order.csv";
        List<OrderEntity> orders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] orderValues = line.split(",");

                Optional<OrderEntity> order = VALIDATION_SERVICE.createOrderEntity(orderValues);

                if (order.isEmpty()) {
                    continue;
                }

                orders.add(order.get());
            }
        } catch (IOException e) {
            log.error("Error while loading data!");
        }

        ORDER_SERVICE.saveAllOrders(orders);
    }

    @Override
    @Transactional
    public void loadMinerGroupData() {
        String csvFile = "src/main/resources/static/minerGroup.csv";
        List<MinerGroupEntity> minerGroups = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] minerGroupValues = line.split(",");

                Optional<MinerGroupEntity> minerGroup = VALIDATION_SERVICE.createMinerGroupEntity(minerGroupValues);
                if(minerGroup.isEmpty()) {
                    continue;
                }

                minerGroups.add(minerGroup.get());
            }
        } catch (IOException e) {
            log.error("Error while loading data!");
        }

        MINER_GROUP_SERVICE.saveAllMinerGroups(minerGroups);
    }

    @Override
    @Transactional
    public void loadMinerData() {
        String csvFile = "src/main/resources/static/miner.csv";
        List<MinerEntity> miners = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] minerValues = line.split(",");
                Optional<MinerEntity> miner = VALIDATION_SERVICE.createMinerEntity(minerValues);

                if(miner.isEmpty()) {
                    continue;
                }

                miners.add(miner.get());
            }
        } catch (IOException e) {
            log.error("Error while loading data!");
        }

        MINER_SERVICE.saveAllMiners(miners);
    }
}
