package com.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.core.service.ServiceImpl.StockServiceImpl;
import com.core.service.ServiceImpl.MinerGroupServiceImpl;
import com.core.service.ServiceImpl.MinerServiceImpl;
import com.core.service.ServiceImpl.OrderServiceImpl;

import com.core.DAO.DAOImpl.MinerDAO;
import com.core.DAO.DAOImpl.MinerGroupDAO;
import com.core.DAO.DAOImpl.OrderDAO;
import com.core.DAO.DAOImpl.StockDAO;

@Configuration
public class ApplicationContextConfig {

    //Config
    @Bean
    public LiquibaseConfig liquibase() {
        return new LiquibaseConfig();
    }

    @Bean
    public HibernateUtil hibernateUtil(){
        return new HibernateUtil();
    }

    //DAO
    @Bean
    public MinerGroupDAO minerGroupDAO() {
        return new MinerGroupDAO(hibernateUtil());
    }

    @Bean
    public StockDAO stockDAO(){
        return new StockDAO(hibernateUtil());
    }

    @Bean
    public MinerDAO minerDAO(HibernateUtil hibernateUtil) {
        return new MinerDAO(hibernateUtil);
    }

    @Bean
    public OrderDAO orderDAO(HibernateUtil hibernateUtil) {
        return new OrderDAO(hibernateUtil);
    }

    //SERVICE
    @Bean
    public MinerServiceImpl minerService(MinerDAO minerDAO){
        return new MinerServiceImpl(minerDAO);
    }

    @Bean
    public MinerGroupServiceImpl minerGroupService(MinerGroupDAO minerGroupDAO){
        return new MinerGroupServiceImpl(minerGroupDAO);
    }

    @Bean
    public OrderServiceImpl orderService(OrderDAO orderDAO){
        return new OrderServiceImpl(orderDAO);
    }

    @Bean
    public StockServiceImpl stockService(StockDAO stockDAO){
        return new StockServiceImpl(stockDAO);
    }
}
