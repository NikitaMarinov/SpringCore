package com.core.constants;

public class Constants {
    public static void minerStatusOutput(){
        System.out.println("Select miner status:");
        System.out.println("1 - CHIEF MINER");
        System.out.println("2 - BOMBER");
        System.out.println("3 - TRAIN DRIVER");
        System.out.println("4 - WORKER");
    }

    public static void orderStatusOutput(){
        System.out.println("Select order status:");
        System.out.println("1 - AWAITING");
        System.out.println("2 - IN PROGRESS");
        System.out.println("3 - COMPLETED");
    }

    public static void orderCreateMenuOutput(){
        System.out.println("0 - EXIT FROM ORDER MENU");
        System.out.println("1 - CREATE ORDER");
        System.out.println("2 - UPDATE ORDER");
        System.out.println("3 - DELETE ORDER");
        System.out.println("4 - FIND ORDER BY ID");
        System.out.println("5 - GET ALL ORDER");

    }

    public static void typeOfOreOutput(){
        System.out.println("Select type of ore:");
        System.out.println("1 - COAL");
        System.out.println("2 - IRON");
        System.out.println("3 - GOLD");
    }

    public static void minerMenuOutput(){
        System.out.println("\n0 - EXIT");
        System.out.println("1 - CREATE MINER");
        System.out.println("2 - UPDATE MINER");
        System.out.println("3 - DELETE MINER");
        System.out.println("4 - FIND MINER BY ID");
        System.out.println("5 - GET ALL MINERS");
    }
    public static void mainMenuOutput(){
        System.out.println("\n0 - EXIT");
        System.out.println("1 - MINER ENTITY");
        System.out.println("2 - ORDER ENTITY");
        System.out.println("3 - STOCK ENTITY");
        System.out.println("4 - MINER GROUP ENTITY");
    }

    public static void mainerChangeMenuOutput(){
        System.out.println("0 - SAVE");
        System.out.println("1 - NAME");
        System.out.println("2 - SURNAME");
        System.out.println("3 - SALARY");
        System.out.println("4 - AT WORK");
        System.out.println("5 - MINER STATUS");
        System.out.println("6 - MINER GROUP");
    }

    public static void orderChangeMenuOutput(){
        System.out.println("0 - SAVE");
        System.out.println("1 - TYPE OF ORE");
        System.out.println("2 - QUANTITY");
        System.out.println("3 - PRICE");
        System.out.println("4 - ORDER STATUS");
        System.out.println("5 - MINER GROUP");
    }

    public static void stockMenuOutput(){
        System.out.println("0 - EXIT FROM STOCK MENU");
        System.out.println("1 - CREATE STOCK");
        System.out.println("2 - UPDATE STOCK");
        System.out.println("3 - DELETE STOCK");
        System.out.println("4 - FIND STOCK BY ID");
        System.out.println("5 - GET ALL STOCK");
    }

    public static void stockChangeMenuOutput(){
        System.out.println("0 - SAVE");
        System.out.println("1 - COAL");
        System.out.println("2 - IRON");
        System.out.println("3 - GOLD");
    }
    public static void minerGroupMenuOutput(){
        System.out.println("0 - EXIT MINER GROUP MENU");
        System.out.println("1 - CREATE MINER GROUP");
        System.out.println("2 - UPDATE MINER GROUP");
        System.out.println("3 - DELETE MINER GROUP");
        System.out.println("4 - FIND MINER GROUP BY ID");
        System.out.println("5 - GET ALL MINER GROUPS");
    }
    public static void minerGroupChangeMenuOutput(){
        System.out.println("0 - SAVE");
        System.out.println("1 - NAME");
        System.out.println("2 - PRODUCTIVITY");
        System.out.println("3 - MINE ORE");
        System.out.println("4 - ORDER");
        System.out.println("5 - STOCK");
    }
}
