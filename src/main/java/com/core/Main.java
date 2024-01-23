package com.core;

import java.util.Optional;
import java.util.Scanner;
import java.util.List;

import com.core.constants.Constants;
import com.core.domain.enums.MinerStatusEnum;
import com.core.domain.enums.OrderStatusEnum;
import com.core.domain.enums.TypeOfOreEnum;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.Session;

import com.core.DAO.DAOImpl.MinerDAO;
import com.core.DAO.DAOImpl.MinerGroupDAO;
import com.core.DAO.DAOImpl.OrderDAO;
import com.core.DAO.DAOImpl.StockDAO;
import com.core.config.HibernateUtil;
import com.core.config.LiquibaseConfig;
import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@Slf4j
public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        HibernateUtil hibernateUtil = applicationContext.getBean(HibernateUtil.class);
        LiquibaseConfig liquibaseConfig = applicationContext.getBean(LiquibaseConfig.class);

        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            liquibaseConfig.configureLiquibase(session);
        }

        MinerGroupDAO minerGroupDAO = applicationContext.getBean(MinerGroupDAO.class);
        MinerDAO minerDAO = applicationContext.getBean(MinerDAO.class);
        StockDAO stockDAO = applicationContext.getBean(StockDAO.class);
        OrderDAO orderDAO = applicationContext.getBean(OrderDAO.class);

        Scanner scanner = new Scanner(System.in);

        int selectedEntity;
        do {

            Constants.mainMenuOutput();

            do {
                System.out.print("Select entity between 0 - 4: ");
                selectedEntity = scanner.nextInt();
            } while (selectedEntity < 0 || selectedEntity > 4);

            switch (selectedEntity) {
                case 1 -> {
                    int selectedMethod;

                    do {

                        Constants.minerMenuOutput();

                        do {
                            System.out.print("Select method between 0 - 5: ");
                            selectedMethod = scanner.nextInt();
                        } while (selectedMethod < 0 || selectedMethod > 5);

                        switch (selectedMethod) {
                            case 1 -> {
                                scanner.nextLine();
                                System.out.print("Enter miner name: ");
                                String name = scanner.nextLine();

                                System.out.print("Enter miner surname: ");
                                String surname = scanner.nextLine();

                                int salary;
                                do {
                                    System.out.print("Enter miner salary: ");
                                    salary = scanner.nextInt();
                                } while (salary < 0);

                                System.out.println(" 0 - Isn't at work");
                                System.out.println(" 1 - Is at work");
                                int atWorkSwitch;
                                do {
                                    atWorkSwitch = scanner.nextInt();
                                } while (atWorkSwitch < 0 || atWorkSwitch > 1);
                                boolean atWork = atWorkSwitch == 1;


                                Constants.minerStatusOutput();
                                int statusSwitch;

                                do {
                                    statusSwitch = scanner.nextInt();
                                } while (statusSwitch < 1 || statusSwitch > 4);


                                MinerStatusEnum minerStatus = null;

                                switch (statusSwitch) {
                                    case 1 -> minerStatus = MinerStatusEnum.CHIEF_MINER;
                                    case 2 -> minerStatus = MinerStatusEnum.BOMBER;
                                    case 3 -> minerStatus = MinerStatusEnum.TRAIN_DRIVER;
                                    case 4 -> minerStatus = MinerStatusEnum.WORKER;
                                }

                                MinerGroupEntity minerGroupEntity = null;
                                Optional<MinerGroupEntity> minerGroupEntityOptional;
                                do {
                                    System.out.print("Select miner group: ");

                                    long minerGroupId = scanner.nextLong();
                                    minerGroupEntityOptional = minerGroupDAO.findById(minerGroupId);

                                    if (minerGroupEntityOptional.isPresent()) {
                                        minerGroupEntity = minerGroupEntityOptional.get();
                                    }

                                } while (minerGroupEntityOptional.isEmpty());

                                minerDAO.save(new MinerEntity()
                                        .setName(name)
                                        .setSurname(surname)
                                        .setSalary(salary)
                                        .setAtWork(atWork)
                                        .setStatus(minerStatus)
                                        .setMinerGroup(minerGroupEntity)
                                );
                            }
                            case 2 -> {
                                long minerId;
                                System.out.println("Enter miner id you want to change!");
                                minerId = scanner.nextInt();

                                Optional<MinerEntity> minerOptional = minerDAO.findById(minerId);

                                if (minerOptional.isEmpty()) {
                                    break;
                                }

                                MinerEntity miner = minerOptional.get();
                                int changedField;

                                do {
                                    Constants.mainerChangeMenuOutput();

                                    do {
                                        changedField = scanner.nextInt();
                                    } while (changedField < 0 || changedField > 6);

                                    switch (changedField) {
                                        case 1 -> {
                                            scanner.nextLine();

                                            System.out.print("Enter miner name: ");
                                            String name = scanner.nextLine();

                                            miner.setName(name);
                                        }
                                        case 2 -> {
                                            scanner.nextLine();

                                            System.out.print("Enter miner surname: ");
                                            String surname = scanner.nextLine();

                                            miner.setSurname(surname);
                                        }
                                        case 3 -> {
                                            int salary;

                                            do {
                                                System.out.print("Enter miner salary: ");
                                                salary = scanner.nextInt();
                                            } while (salary < 0);

                                            miner.setSalary(salary);
                                        }
                                        case 4 -> {
                                            System.out.println(" 0 - Isn't at work");
                                            System.out.println(" 1 - Is at work");

                                            int atWorkSwitch;

                                            do {
                                                atWorkSwitch = scanner.nextInt();
                                            } while (atWorkSwitch < 0 || atWorkSwitch > 1);
                                            boolean atWork = atWorkSwitch == 1;

                                            miner.setAtWork(atWork);
                                        }
                                        case 5 -> {
                                            int statusSwitch;

                                            do {
                                                statusSwitch = scanner.nextInt();
                                            } while (statusSwitch < 1 || statusSwitch > 4);

                                            MinerStatusEnum minerStatus = null;

                                            switch (statusSwitch) {
                                                case 1 -> minerStatus = MinerStatusEnum.CHIEF_MINER;
                                                case 2 -> minerStatus = MinerStatusEnum.BOMBER;
                                                case 3 -> minerStatus = MinerStatusEnum.TRAIN_DRIVER;
                                                case 4 -> minerStatus = MinerStatusEnum.WORKER;
                                            }

                                            miner.setStatus(minerStatus);
                                        }
                                        case 6 -> {
                                            MinerGroupEntity minerGroupEntity = null;
                                            Optional<MinerGroupEntity> minerGroupEntityOptional;

                                            do {
                                                System.out.print("Select miner group: ");
                                                long minerGroupId = scanner.nextLong();

                                                minerGroupEntityOptional = minerGroupDAO.findById(minerGroupId);

                                                if (minerGroupEntityOptional.isPresent()) {
                                                    minerGroupEntity = minerGroupEntityOptional.get();

                                                }
                                            } while (minerGroupEntityOptional.isEmpty());

                                            miner.setMinerGroup(minerGroupEntity);
                                        }
                                    }
                                } while (changedField != 0);
                                minerDAO.update(miner);
                            }
                            case 3 -> {
                                System.out.print("Enter miner ID for deletion:");
                                long minerId;

                                do {
                                    minerId = scanner.nextLong();
                                } while (minerId < 0 || minerId > 100000);

                                minerDAO.delete(minerId);
                            }
                            case 4 -> {
                                long minerId;

                                do {
                                    minerId = scanner.nextLong();
                                } while (minerId < 0 || minerId > 100000);

                                Optional<MinerEntity> miner = minerDAO.findById(minerId);

                                miner.ifPresent(System.out::println);
                            }
                            case 5 -> {
                                Optional<List<MinerEntity>> minerEntityList = minerDAO.findAll();

                                if (minerEntityList.isPresent()) {
                                    MinerEntity.toStringList(minerEntityList.get());
                                } else {
                                    log.warn("The miner table is empty!");
                                }
                            }
                        }
                    } while (selectedMethod != 0);
                }
                case 2 -> {
                    int selectedMethod;
                    do {
                        Constants.orderCreateMenuOutput();

                        do {
                            System.out.print("Select method between 0 - 5: ");
                            selectedMethod = scanner.nextInt();
                        } while (selectedMethod < 0 || selectedMethod > 5);

                        switch (selectedMethod) {
                            case 1 -> {
                                Constants.typeOfOreOutput();

                                int typeOfOreSwitch;
                                do {
                                    typeOfOreSwitch = scanner.nextInt();
                                } while (typeOfOreSwitch < 1 || typeOfOreSwitch > 3);

                                TypeOfOreEnum typeOfOre = null;

                                switch (typeOfOreSwitch) {
                                    case 1 -> typeOfOre = TypeOfOreEnum.COAL;
                                    case 2 -> typeOfOre = TypeOfOreEnum.IRON;
                                    case 3 -> typeOfOre = TypeOfOreEnum.GOLD;
                                }

                                int quantity;
                                do {
                                    System.out.print("Enter miner quantity: ");
                                    quantity = scanner.nextInt();
                                } while (quantity < 0);

                                int price;
                                do {
                                    System.out.print("Enter miner price: ");
                                    price = scanner.nextInt();
                                } while (price < 0);

                                Constants.orderStatusOutput();

                                int orderStatusSwitch;
                                do {
                                    orderStatusSwitch = scanner.nextInt();
                                } while (orderStatusSwitch < 1 || orderStatusSwitch > 3);

                                OrderStatusEnum orderStatus = null;

                                switch (orderStatusSwitch) {
                                    case 1 -> orderStatus = OrderStatusEnum.AWAITING;
                                    case 2 -> orderStatus = OrderStatusEnum.IN_PROGRESS;
                                    case 3 -> orderStatus = OrderStatusEnum.COMPLETED;
                                }

                                MinerGroupEntity minerGroupEntity = null;
                                Optional<MinerGroupEntity> minerGroupEntityOptional;

                                do {
                                    System.out.print("Select miner group: ");
                                    long minerGroupId = scanner.nextLong();

                                    minerGroupEntityOptional = minerGroupDAO.findById(minerGroupId);

                                    if (minerGroupEntityOptional.isPresent()) {
                                        minerGroupEntity = minerGroupEntityOptional.get();

                                    }
                                } while (minerGroupEntityOptional.isEmpty());

                                orderDAO.save(new OrderEntity()
                                        .setPrice(price)
                                        .setQuantity(quantity)
                                        .setTypeOfOre(typeOfOre)
                                        .setOrderStatus(orderStatus)
                                        .setMinerGroup(minerGroupEntity));
                            }
                            case 2 -> {
                                long orderId;
                                System.out.println("Enter miner id you want to change!");
                                orderId = scanner.nextInt();

                                Optional<OrderEntity> orderOptional = orderDAO.findById(orderId);
                                if (orderOptional.isEmpty()) {
                                    break;
                                }

                                OrderEntity order = orderOptional.get();
                                int changedField;
                                do {
                                    Constants.orderChangeMenuOutput();

                                    do {
                                        changedField = scanner.nextInt();
                                    } while (changedField < 0 || changedField > 5);

                                    switch (changedField) {
                                        case 1 -> {
                                            Constants.typeOfOreOutput();

                                            int typeOfOreSwitch;
                                            do {
                                                typeOfOreSwitch = scanner.nextInt();
                                            } while (typeOfOreSwitch < 1 || typeOfOreSwitch > 3);

                                            TypeOfOreEnum typeOfOre = null;

                                            switch (typeOfOreSwitch) {
                                                case 1 -> typeOfOre = TypeOfOreEnum.COAL;
                                                case 2 -> typeOfOre = TypeOfOreEnum.IRON;
                                                case 3 -> typeOfOre = TypeOfOreEnum.GOLD;
                                            }

                                            order.setTypeOfOre(typeOfOre);
                                        }
                                        case 2 -> {
                                            int quantity;

                                            do {
                                                System.out.print("Enter miner quantity: ");
                                                quantity = scanner.nextInt();
                                            } while (quantity < 0);

                                            order.setQuantity(quantity);
                                        }
                                        case 3 -> {
                                            int price;

                                            do {
                                                System.out.print("Enter miner price: ");
                                                price = scanner.nextInt();
                                            } while (price < 0);

                                            order.setPrice(price);
                                        }
                                        case 4 -> {
                                            Constants.orderStatusOutput();

                                            int orderStatusSwitch;
                                            do {
                                                orderStatusSwitch = scanner.nextInt();
                                            } while (orderStatusSwitch < 1 || orderStatusSwitch > 3);

                                            OrderStatusEnum orderStatus = null;

                                            switch (orderStatusSwitch) {
                                                case 1 -> orderStatus = OrderStatusEnum.AWAITING;
                                                case 2 -> orderStatus = OrderStatusEnum.IN_PROGRESS;
                                                case 3 -> orderStatus = OrderStatusEnum.COMPLETED;
                                            }

                                            order.setOrderStatus(orderStatus);
                                        }
                                        case 5 -> {
                                            MinerGroupEntity minerGroupEntity = null;
                                            Optional<MinerGroupEntity> minerGroupEntityOptional;

                                            do {
                                                System.out.print("Select miner group: ");
                                                long minerGroupId = scanner.nextLong();

                                                minerGroupEntityOptional = minerGroupDAO.findById(minerGroupId);

                                                if (minerGroupEntityOptional.isPresent()) {
                                                    minerGroupEntity = minerGroupEntityOptional.get();
                                                }

                                            } while (minerGroupEntityOptional.isEmpty());
                                            order.setMinerGroup(minerGroupEntity);
                                        }
                                    }
                                } while (changedField != 0);

                                orderDAO.update(order);
                            }
                            case 3 -> {
                                System.out.print("Enter order ID for deletion:");

                                long orderId;
                                do {
                                    orderId = scanner.nextLong();
                                } while (orderId < 0 || orderId > 100000);

                                orderDAO.delete(orderId);
                            }
                            case 4 -> {
                                System.out.println("Select order ID:");
                                long orderId;

                                do {
                                    orderId = scanner.nextLong();
                                } while (orderId < 0 || orderId > 100000);

                                Optional<OrderEntity> miner = orderDAO.findById(orderId);

                                miner.ifPresent(System.out::println);
                            }
                            case 5 -> {
                                Optional<List<OrderEntity>> orderList = orderDAO.findAll();

                                if (orderList.isPresent()) {
                                    OrderEntity.toStringList(orderList.get());
                                } else {
                                    log.warn("The miner table is empty!");
                                }
                            }
                        }
                    } while (selectedMethod != 0);
                }
                case 3 -> {
                    int selectedMethod;
                    do {
                        Constants.stockMenuOutput();

                        do {
                            System.out.print("Select method between 0 - 5: ");
                            selectedMethod = scanner.nextInt();
                        } while (selectedMethod < 0 || selectedMethod > 5);

                        switch (selectedMethod) {
                            case 1 -> {
                                int coal;
                                do {
                                    System.out.print("Enter quantity of coal: ");
                                    coal = scanner.nextInt();
                                } while (coal < 0);

                                int iron;
                                do {
                                    System.out.print("Enter quantity of iron: ");
                                    iron = scanner.nextInt();
                                } while (iron < 0);

                                int gold;
                                do {
                                    System.out.print("Enter quantity of gold: ");
                                    gold = scanner.nextInt();
                                } while (gold < 0);

                                stockDAO.save(
                                        new StockEntity()
                                                .setCoal(coal)
                                                .setIron(iron)
                                                .setCoal(gold)
                                );
                            }
                            case 2 -> {
                                long stockId;
                                System.out.println("Enter stock id you want to change!");

                                stockId = scanner.nextInt();
                                Optional<StockEntity> stockOptional = stockDAO.findById(stockId);

                                if (stockOptional.isEmpty()) {
                                    break;
                                }

                                StockEntity stock = stockOptional.get();
                                int changedField;

                                do {
                                    Constants.stockChangeMenuOutput();
                                    do {
                                        changedField = scanner.nextInt();
                                    } while (changedField < 0 || changedField > 3);

                                    switch (changedField) {
                                        case 1 -> {

                                            int coal;
                                            do {
                                                System.out.print("Enter the quantity of coal: ");
                                                coal = scanner.nextInt();
                                            } while (coal < 0);

                                            stock.setCoal(coal);
                                        }
                                        case 2 -> {
                                            int iron;

                                            do {
                                                System.out.print("Enter the quantity of iron: ");
                                                iron = scanner.nextInt();
                                            } while (iron < 0);

                                            stock.setIron(iron);
                                        }
                                        case 3 -> {
                                            int gold;

                                            do {
                                                System.out.print("Enter the quantity of gold: ");
                                                gold = scanner.nextInt();
                                            } while (gold < 0);

                                            stock.setGold(gold);
                                        }
                                    }
                                } while (changedField != 0);
                                stockDAO.update(stock);
                            }
                            case 3 -> {
                                System.out.print("Enter stock ID for deletion:");
                                long stockId;

                                do {
                                    stockId = scanner.nextLong();
                                } while (stockId < 0 || stockId > 100000);

                                stockDAO.delete(stockId);
                            }
                            case 4 -> {
                                System.out.println("Enter the Stock ID:");
                                long stockId;

                                do {
                                    stockId = scanner.nextLong();
                                } while (stockId < 0 || stockId > 100000);

                                Optional<StockEntity> miner = stockDAO.findById(stockId);

                                miner.ifPresent(System.out::println);
                            }
                            case 5 -> {
                                Optional<List<StockEntity>> stockList = stockDAO.findAll();

                                if (stockList.isPresent()) {
                                    StockEntity.toStringList(stockList.get());
                                } else {
                                    log.warn("The stock table is empty!");
                                }
                            }
                        }
                    } while (selectedMethod != 0);
                }

                case 4 -> {
                    int selectedMethod;
                    do {
                        Constants.minerGroupMenuOutput();
                        do {
                            System.out.print("Select method between 0 - 5: ");
                            selectedMethod = scanner.nextInt();
                        } while (selectedMethod < 0 || selectedMethod > 5);

                        switch (selectedMethod) {
                            case 1 -> {
                                scanner.nextLine();

                                System.out.print("Enter miner group name: ");
                                String minerGroupName = scanner.nextLine();

                                int productivity;
                                do {
                                    System.out.print("Enter the productivity of miner group: ");
                                    productivity = scanner.nextInt();
                                } while (productivity < 0);

                                Constants.typeOfOreOutput();

                                int minerOreSwitch;
                                do {
                                    minerOreSwitch = scanner.nextInt();
                                } while (minerOreSwitch < 1 || minerOreSwitch > 3);

                                TypeOfOreEnum mineOre = null;

                                switch (minerOreSwitch) {
                                    case 1 -> mineOre = TypeOfOreEnum.COAL;
                                    case 2 -> mineOre = TypeOfOreEnum.IRON;
                                    case 3 -> mineOre = TypeOfOreEnum.GOLD;
                                }

                                StockEntity stockEntity = null;
                                Optional<StockEntity> stockEntityOptional;

                                do {
                                    System.out.print("Select stock: ");
                                    long stockId = scanner.nextLong();

                                    stockEntityOptional = stockDAO.findById(stockId);

                                    if (stockEntityOptional.isPresent()) {
                                        stockEntity = stockEntityOptional.get();

                                    }
                                } while (stockEntityOptional.isEmpty());


                                OrderEntity orderEntity = null;
                                Optional<OrderEntity> orderEntityOptional;

                                do {
                                    System.out.print("Select stock: ");
                                    long orderId = scanner.nextLong();

                                    orderEntityOptional = orderDAO.findById(orderId);

                                    if (orderEntityOptional.isPresent()) {
                                        orderEntity = orderEntityOptional.get();

                                    }
                                } while (orderEntityOptional.isEmpty());

                                minerGroupDAO.save(new MinerGroupEntity()
                                        .setName(minerGroupName)
                                        .setMineOre(mineOre)
                                        .setProductivity(productivity)
                                        .setOrder(orderEntity)
                                        .setStock(stockEntity)
                                );
                            }
                            case 2 -> {
                                long minerGroupId;
                                System.out.println("Enter miner group id you want to change!");
                                minerGroupId = scanner.nextInt();

                                Optional<MinerGroupEntity> minerGroupEntityOptional = minerGroupDAO.findById(minerGroupId);
                                if (minerGroupEntityOptional.isEmpty()) {
                                    break;
                                }

                                MinerGroupEntity minerGroup = minerGroupEntityOptional.get();

                                int changedField;
                                do {
                                    Constants.minerGroupChangeMenuOutput();

                                    do {
                                        changedField = scanner.nextInt();
                                    } while (changedField < 0 || changedField > 5);

                                    switch (changedField) {
                                        case 1 -> {
                                            scanner.nextLine();
                                            System.out.print("Enter miner group name: ");
                                            String minerGroupName = scanner.nextLine();

                                            minerGroup.setName(minerGroupName);
                                        }
                                        case 2 -> {
                                            int productivity;

                                            do {
                                                System.out.print("Enter the productivity of miner group: ");
                                                productivity = scanner.nextInt();
                                            } while (productivity < 0);

                                            minerGroup.setProductivity(productivity);
                                        }
                                        case 3 -> {
                                            Constants.typeOfOreOutput();

                                            int minerOreSwitch;
                                            do {
                                                minerOreSwitch = scanner.nextInt();
                                            } while (minerOreSwitch < 1 || minerOreSwitch > 3);

                                            TypeOfOreEnum mineOre = null;

                                            switch (minerOreSwitch) {
                                                case 1 -> mineOre = TypeOfOreEnum.COAL;
                                                case 2 -> mineOre = TypeOfOreEnum.IRON;
                                                case 3 -> mineOre = TypeOfOreEnum.GOLD;
                                            }

                                            minerGroup.setMineOre(mineOre);
                                        }
                                        case 4 -> {

                                            OrderEntity orderEntity = null;
                                            Optional<OrderEntity> orderEntityOptional;

                                            do {
                                                System.out.print("Select stock: ");
                                                long orderId = scanner.nextLong();

                                                orderEntityOptional = orderDAO.findById(orderId);

                                                if (orderEntityOptional.isPresent()) {
                                                    orderEntity = orderEntityOptional.get();
                                                }

                                            } while (orderEntityOptional.isEmpty());

                                            minerGroup.setOrder(orderEntity);
                                        }
                                        case 5 -> {

                                            StockEntity stockEntity = null;
                                            Optional<StockEntity> stockEntityOptional;

                                            do {
                                                System.out.print("Select stock: ");
                                                long stockId = scanner.nextLong();

                                                stockEntityOptional = stockDAO.findById(stockId);

                                                if (stockEntityOptional.isPresent()) {
                                                    stockEntity = stockEntityOptional.get();
                                                }

                                            } while (stockEntityOptional.isEmpty());

                                            minerGroup.setStock(stockEntity);
                                        }
                                    }
                                } while (changedField != 0);
                                minerGroupDAO.update(minerGroup);
                            }
                            case 3 -> {
                                System.out.print("Enter miner group ID for deletion: ");
                                long minerGroupId;

                                do {
                                    minerGroupId = scanner.nextLong();
                                } while (minerGroupId < 0 || minerGroupId > 100000);

                                minerGroupDAO.delete(minerGroupId);
                            }
                            case 4 -> {
                                long minerGroupId;

                                do {
                                    minerGroupId = scanner.nextLong();
                                } while (minerGroupId < 0 || minerGroupId > 100000);

                                Optional<MinerGroupEntity> miner = minerGroupDAO.findById(minerGroupId);

                                miner.ifPresent(System.out::println);
                            }
                            case 5 -> {
                                Optional<List<MinerGroupEntity>> minerGroupList = minerGroupDAO.findAll();
                                if (minerGroupList.isPresent()) {
                                    MinerGroupEntity.toStringList(minerGroupList.get());
                                } else {
                                    log.warn("The miner table is empty!");
                                }
                            }
                        }
                    } while (selectedMethod != 0);
                }
                default -> {
                }
            }
        } while (selectedEntity != 0);
        scanner.close();
    }
}