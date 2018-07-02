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

    public static void main(String[] args) throws InterruptedException, SocketTimeoutException {
        OrderMonitor or = new OrderMonitor();
        double prise = Calculation.getFormatPrise(Double.parseDouble(Modules.getPrise(Bot_Action.key, Bot_Action.secret, Bot_Action.pair).get("1").toString()), "#0.0000");

        String orderTupe_1 = OrderTupes.sellOrder(6000, 2);
        String orderTupe_2 = "";
        String orderTupe_3 = "";

        System.out.println(orderTupe_1);
  
        or.start();
        while (true) {
            Thread.sleep(1000);

            if (OrderMonitor.getMonitRes().toString().equalsIgnoreCase(getOrderId(orderTupe_1))) {
                System.out.println("Сработал orderTupe_1 prise" + getOrderPrise(orderTupe_1));
                System.out.println(OrderMonitor.getMonitRes());
            }
            System.out.println(OrderMonitor.getMonitRes());
            if (OrderMonitor.getMonitRes().toString().equalsIgnoreCase(getOrderId(orderTupe_2))) {
                System.out.println("Сработал orderTupe_2");
                System.out.println(OrderMonitor.getMonitRes());
            }
            if (OrderMonitor.getMonitRes().toString().equalsIgnoreCase(getOrderId(orderTupe_3))) {
                System.out.println("Сработал orderTupe_3");
                System.out.println(OrderMonitor.getMonitRes());
            }

        }

    }
}
