/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class Algoritm {
//Ордер на продажу ETH

//    public static String orderSell(double trustedLimitETH, double trustedLimitUSD, double persProfit, double prise) {
//        double orderPrise = Calculation.getFormatPrise(Calculation.getOrderSellPrise(Bot_Action.pair, Bot_Action.getOrderCount(), persProfit, trustedLimitUSD, Bot_Action.key, Bot_Action.secret, Bot_Action.getAverageOrCurent(), prise), "#0.00");
//        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(trustedLimitETH), String.valueOf(orderPrise), "sell");
//        return e;
//    }
//
//    public static String orderBuy(double trustedLimitETH, double trustedLimitUSD, double persProfit, double prise) throws InterruptedException, SocketTimeoutException {
//        double quantiti = trustedLimitUSD / prise;
//        double orderPrise = Calculation.getFormatPrise(Calculation.getOrderBuyPrise(Bot_Action.pair, persProfit, Bot_Action.key, Bot_Action.secret, trustedLimitUSD, prise), "#0.00");
//        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(quantiti), String.valueOf(orderPrise), "buy");
//        return e;
//    }
    public static String priseMonitor(double oldPrise, double prise) {
        String info = "";
        if (prise > (oldPrise + 1)) {
            info = "priseUp";
        } else if (prise < (oldPrise - 1)) {
            info = "priseDown";
        } else {
            info = "no change";
        }
        return info;
    }

    public static HashMap getDelleteOrder(String key, String secret, String newOrd, String oldOrd) {
        HashMap<String, String> tmp = new HashMap<>();
        String tm = "";
        if (newOrd.length() > 5 && oldOrd.length() > 5) {
            String[] newOrdd = newOrd.replace("order", "rim order").split("rim ");
            String[] oldOrdd = oldOrd.replace("order", "rim order").split("rim ");
            int it = 0;
            for (int i = 0; i < oldOrdd.length; i++) {
                for (int j = 0; j < newOrdd.length; j++) {
                    if (oldOrdd[i].equalsIgnoreCase(newOrdd[j])) {

                        it++;
                    }
                }
                if (it == 0) {
                    tm = oldOrdd[i];
//                    oldOrdd[i] = oldOrdd[i].substring(oldOrdd[i].indexOf("type"), oldOrdd[i].indexOf("type") + 8);
//                    oldOrdd[i] = oldOrdd[i].concat(" " + tm.substring(tm.indexOf("price"), tm.length()));
                    oldOrdd[i] = oldOrdd[i].substring(oldOrdd[i].indexOf("order_id:"), oldOrdd[i].indexOf("order_id:") + 18);
                    tmp.put("delOrd", oldOrdd[i]);
                }
                it = 0;
            }
        } else {
            tm = oldOrd;
//            oldOrd = oldOrd.substring(oldOrd.indexOf("type"), oldOrd.indexOf("type") + 8);
//            oldOrd = oldOrd.concat(" " + tm.substring(tm.indexOf("price"), tm.length()));
            oldOrd = oldOrd.substring(oldOrd.indexOf("order_id:"), oldOrd.indexOf("order_id:") + 18);
            tmp.put("delOrd", oldOrd);
        }
        return tmp;
    }

    public static String getOrderId(String order) {
        if (order.length() > 5) {
            order = order.replaceAll("\"", "");
            order = order.substring(order.indexOf("order_id:"), order.indexOf("order_id:") + 18);
        } else {
            order = "0";
        }
        return order;
    }

    public static String getOrderPrise(String order) {
        if (order.length() > 5) {
            order = order.replaceAll("\"", "");
            order = order.substring(order.indexOf("orderPrise") + 11, order.length());
        } else {
            order = "0";
        }
        return order;
    }

    public static String getOrderTupe(String order) {
        if (order.length() > 5) {
            order = order.replaceAll("\"", "");
            order = order.substring(order.indexOf("tupe") + 5, order.indexOf("tupe") + 8);
        } else {
            order = "0";
        }
        return order;
    }

    public static void main(String[] args) throws InterruptedException, SocketTimeoutException {
        OrderMonitor or = new OrderMonitor();
        double prise = Calculation.getFormatPrise(Double.parseDouble(Modules.getPrise(Bot_Action.key, Bot_Action.secret, Bot_Action.pair).get("1").toString()), "#0.0000");

        String orderTupe_1 = OrderTupes.sellOrder(6000, 2);
        String orderTupe_1_Ind = "";
        double orderTupe_1_PrSel = 0.0;
        double orderTupe_1_PrBuy = 0.0;

        String orderTupe_2 = "";
        String orderTupe_2_Ind = "";
        double orderTupe_2_PrSel = 0.0;
        double orderTupe_2_PrBuy = 0.0;

        String orderTupe_3 = "";
        String orderTupe_3_Ind = "";

        System.out.println(getOrderTupe(orderTupe_1));

        or.start();
        while (true) {
            if (prise > orderTupe_1_PrSel + 2 && orderTupe_1_Ind.equalsIgnoreCase("sellDoneTupe1")) {
                //нужно создать (sell)
                orderTupe_2 = OrderTupes.sellOrder(prise, 2);
            }
            if (prise < orderTupe_1_PrBuy - 2 && orderTupe_1_Ind.equalsIgnoreCase("buyDoneTupe1") && orderTupe_2_Ind.equalsIgnoreCase("sellDoneTupe2")) {
                //нужно создать (buy)
                orderTupe_2 = OrderTupes.buyOrder(prise, 2);
            }
            if (prise > orderTupe_2_PrSel + 30 && orderTupe_2_Ind.equalsIgnoreCase("sellDoneTupe2")) {
                //нужно создать (sell)
                orderTupe_3 = OrderTupes.sellOrder(prise, 2);
            }
            if (prise < orderTupe_2_PrBuy - 30 && orderTupe_2_Ind.equalsIgnoreCase("buyDoneTupe2") && orderTupe_3_Ind.equalsIgnoreCase("sellDoneTupe3")) {
                //нужно создать (buy)
                orderTupe_3 = OrderTupes.buyOrder(prise, 2);
            }
            Thread.sleep(1000);
            if (OrderMonitor.getMonitRes().toString().equalsIgnoreCase(getOrderId(orderTupe_1))) {
                switch (getOrderTupe(orderTupe_1)) {
                    case "sel":
                        System.out.println("Сработал orderTupe_1 prise sell " + getOrderPrise(orderTupe_1));
                        orderTupe_1_PrSel = Double.valueOf(getOrderPrise(orderTupe_1));
                        //нужно создать (buy)
                        orderTupe_1 = OrderTupes.sellOrder(5000, 2);
                        orderTupe_1_Ind = "sellDoneTupe1";
                        break;
                    case "buy":
                        System.out.println("Сработал orderTupe_1 prise buy " + getOrderPrise(orderTupe_1));
                        orderTupe_1_PrBuy = Double.valueOf(getOrderPrise(orderTupe_1));
                        //нужно создать (sell)
                        orderTupe_1 = OrderTupes.sellOrder(4000, 2);
                        orderTupe_1_Ind = "buyDoneTupe1";
                        break;
                }
            }
            if (OrderMonitor.getMonitRes().toString().equalsIgnoreCase(getOrderId(orderTupe_2))) {
                switch (getOrderTupe(orderTupe_2)) {
                    case "sel":
                        System.out.println("Сработал orderTupe_1 prise sell " + getOrderPrise(orderTupe_2));
                        orderTupe_2_PrSel = Double.valueOf(getOrderPrise(orderTupe_2));
                        orderTupe_2_Ind = "sellDoneTupe2";
                        break;
                    case "buy":
                        System.out.println("Сработал orderTupe_1 prise buy " + getOrderPrise(orderTupe_1));
                        orderTupe_1_PrBuy = Double.valueOf(getOrderPrise(orderTupe_1));
                        orderTupe_2_Ind = "buyDoneTupe2";
                        break;
                }
            }
            if (OrderMonitor.getMonitRes().toString().equalsIgnoreCase(getOrderId(orderTupe_3))) {
                switch (getOrderTupe(orderTupe_3)) {
                    case "sel":
                        System.out.println("Сработал orderTupe_1 prise sell " + getOrderPrise(orderTupe_3));
                        orderTupe_3_Ind = "sellDoneTupe3";
                        break;
                    case "buy":
                        System.out.println("Сработал orderTupe_1 prise buy " + getOrderPrise(orderTupe_3));
                        orderTupe_3_Ind = "buyDoneTupe3";
                        break;
                }
            }

        }

    }
}
