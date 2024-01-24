package com.core;

import com.core.config.ApplicationContextConfig;
import com.core.config.HibernateUtil;
import com.core.config.LiquibaseConfig;
import com.core.constants.Constants;
import com.core.domain.MinerEntity;
import com.core.domain.MinerGroupEntity;
import com.core.domain.OrderEntity;
import com.core.domain.StockEntity;
import com.core.domain.enums.MinerStatusEnum;
import com.core.domain.enums.OrderStatusEnum;
import com.core.domain.enums.TypeOfOreEnum;
import com.core.service.*;
import com.core.service.ServiceImpl.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;


@Slf4j
public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
        HibernateUtil hibernateUtil = applicationContext.getBean(HibernateUtil.class);
        LiquibaseConfig liquibaseConfig = applicationContext.getBean(LiquibaseConfig.class);

        try (Session session = hibernateUtil.getSessionFactory().openSession()) {
            liquibaseConfig.configureLiquibase(session);
        }

        MinerGroupService minerGroupService = applicationContext.getBean(MinerGroupServiceImpl.class);
        MinerService minerService = applicationContext.getBean(MinerServiceImpl.class);
        StockService stockService = applicationContext.getBean(StockServiceImpl.class);
        OrderService orderService = applicationContext.getBean(OrderServiceImpl.class);
        CsvReadeService csvReadeService= applicationContext.getBean(CsvReadeServiceImpl.class);

        Scanner scanner = new Scanner(System.in);

        int selectedEntity;
        do {

            Constants.mainMenuOutput();

            do {
                System.out.print("Select entity between 0 - 5: ");
                selectedEntity = scanner.nextInt();
            } while (selectedEntity < 0 || selectedEntity > 5);

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
                                do {
                                    System.out.print("Select miner group: ");

                                    long minerGroupId = scanner.nextLong();
                                    minerGroupEntity = minerGroupService.findMinerGroupById(minerGroupId);


                                } while (minerGroupEntity == null);

                                minerService.createMinerGroup(new MinerEntity()
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

                                MinerEntity miner = minerService.findMinerById(minerId);

                                if (miner == null) {
                                    break;
                                }

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

                                            do {
                                                System.out.print("Select miner group: ");
                                                long minerGroupId = scanner.nextLong();

                                                minerGroupEntity = minerGroupService.findMinerGroupById(minerGroupId);


                                            } while (minerGroupEntity == null);

                                            miner.setMinerGroup(minerGroupEntity);
                                        }
                                    }
                                } while (changedField != 0);
                                minerService.createMinerGroup(miner);
                            }
                            case 3 -> {
                                System.out.print("Enter miner ID for deletion:");
                                long minerId;

                                do {
                                    minerId = scanner.nextLong();
                                } while (minerId < 0 || minerId > 100000);

                                minerService.deleteMiner(minerId);
                            }
                            case 4 -> {
                                long minerId;

                                do {
                                    minerId = scanner.nextLong();
                                } while (minerId < 0 || minerId > 100000);

                                MinerEntity miner = minerService.findMinerById(minerId);

                                System.out.println(miner);
                            }
                            case 5 -> {
                                List<MinerEntity> minerEntityList = minerService.findAllMinerGroups();

                                if (minerEntityList.isEmpty()) {
                                    log.warn("The miner table is empty!");
                                } else {
                                    MinerEntity.toStringList(minerEntityList);
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

                                do {
                                    System.out.print("Select miner group: ");
                                    long minerGroupId = scanner.nextLong();

                                    minerGroupEntity= minerGroupService.findMinerGroupById(minerGroupId);

                                } while (minerGroupEntity == null);

                                orderService.createOrderGroup(new OrderEntity()
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

                                OrderEntity order = orderService.findOrderById(orderId);
                                if (order == null) {
                                    break;
                                }

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

                                            do {
                                                System.out.print("Select miner group: ");
                                                long minerGroupId = scanner.nextLong();

                                                minerGroupEntity = minerGroupService.findMinerGroupById(minerGroupId);

                                            } while (minerGroupEntity == null);
                                            order.setMinerGroup(minerGroupEntity);
                                        }
                                    }
                                } while (changedField != 0);

                                orderService.updateOrderGroup(order);
                            }
                            case 3 -> {
                                System.out.print("Enter order ID for deletion:");

                                long orderId;
                                do {
                                    orderId = scanner.nextLong();
                                } while (orderId < 0 || orderId > 100000);

                                orderService.deleteOrder(orderId);
                            }
                            case 4 -> {
                                System.out.println("Select order ID:");
                                long orderId;

                                do {
                                    orderId = scanner.nextLong();
                                } while (orderId < 0 || orderId > 100000);

                                OrderEntity order = orderService.findOrderById(orderId);

                                System.out.println(order);
                            }
                            case 5 -> {
                                List<OrderEntity> orderList = orderService.findAllOrderGroups();

                                if (orderList.isEmpty()) {
                                    log.warn("The miner table is empty!");
                                } else {
                                    OrderEntity.toStringList(orderList);
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

                                stockService.createStockGroup(
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
                                StockEntity stock = stockService.findStockById(stockId);

                                if (stock == null) {
                                    break;
                                }

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
                                stockService.updateStockGroup(stock);
                            }
                            case 3 -> {
                                System.out.print("Enter stock ID for deletion:");
                                long stockId;

                                do {
                                    stockId = scanner.nextLong();
                                } while (stockId < 0 || stockId > 100000);

                                stockService.deleteStock(stockId);
                            }
                            case 4 -> {
                                System.out.println("Enter the Stock ID:");
                                long stockId;

                                do {
                                    stockId = scanner.nextLong();
                                } while (stockId < 0 || stockId > 100000);

                                StockEntity stock = stockService.findStockById(stockId);

                                System.out.println(stock);
                            }
                            case 5 -> {
                                List<StockEntity> stockList = stockService.findAllStockGroups();

                                if (stockList.isEmpty()) {
                                    log.warn("The stock table is empty!");
                                } else {
                                    StockEntity.toStringList(stockList);
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

                                do {
                                    System.out.print("Select stock: ");
                                    long stockId = scanner.nextLong();

                                    stockEntity = stockService.findStockById(stockId);


                                } while (stockEntity == null);


                                OrderEntity orderEntity = null;

                                do {
                                    System.out.print("Select stock: ");
                                    long orderId = scanner.nextLong();

                                    orderEntity = orderService.findOrderById(orderId);

                                } while (orderEntity == null);

                                minerGroupService.createMinerGroup(new MinerGroupEntity()
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

                                MinerGroupEntity minerGroup = minerGroupService.findMinerGroupById(minerGroupId);
                                if (minerGroup == null) {
                                    break;
                                }

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

                                            do {
                                                System.out.print("Select stock: ");
                                                long orderId = scanner.nextLong();

                                                orderEntity = orderService.findOrderById(orderId);


                                            } while (orderEntity == null);

                                            minerGroup.setOrder(orderEntity);
                                        }
                                        case 5 -> {

                                            StockEntity stockEntity = null;

                                            do {
                                                System.out.print("Select stock: ");
                                                long stockId = scanner.nextLong();

                                                stockEntity = stockService.findStockById(stockId);


                                            } while (stockEntity == null);

                                            minerGroup.setStock(stockEntity);
                                        }
                                    }
                                } while (changedField != 0);
                                minerGroupService.createMinerGroup(minerGroup);
                            }
                            case 3 -> {
                                System.out.print("Enter miner group ID for deletion: ");
                                long minerGroupId;

                                do {
                                    minerGroupId = scanner.nextLong();
                                } while (minerGroupId < 0 || minerGroupId > 100000);

                                minerGroupService.deleteMinerGroup(minerGroupId);
                            }
                            case 4 -> {
                                long minerGroupId;

                                do {
                                    minerGroupId = scanner.nextLong();
                                } while (minerGroupId < 0 || minerGroupId > 100000);

                                MinerGroupEntity minerGroup = minerGroupService.findMinerGroupById(minerGroupId);

                                System.out.println(minerGroup);
                            }
                            case 5 -> {
                                List<MinerGroupEntity> minerGroupList = minerGroupService.findAllMinerGroups();
                                if (minerGroupList.isEmpty()) {
                                    log.warn("The miner table is empty!");
                                } else {
                                    MinerGroupEntity.toStringList(minerGroupList);
                                }
                            }
                        }
                    } while (selectedMethod != 0);
                }
                case 5 -> {
                    csvReadeService.loadData();
                }
                default -> {
                }
            }
        } while (selectedEntity != 0);
        scanner.close();
    }
}