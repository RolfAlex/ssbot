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

    public static String orderSell(double trustedLimitETH, double trustedLimitUSD, double persProfit, double prise) {
        double orderPrise = Calculation.getFormatPrise(Calculation.getOrderSellPrise(Bot_Action.pair, Bot_Action.getOrderCount(), persProfit, trustedLimitUSD, Bot_Action.key, Bot_Action.secret, Bot_Action.getAverageOrCurent(), prise), "#0.00");
        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(trustedLimitETH), String.valueOf(orderPrise), "sell");
        return e;
    }

    public static String orderBuy(double trustedLimitETH, double trustedLimitUSD, double persProfit, double prise) throws InterruptedException, SocketTimeoutException {
        double quantiti = trustedLimitUSD / prise;
        double orderPrise = Calculation.getFormatPrise(Calculation.getOrderBuyPrise(Bot_Action.pair, persProfit, Bot_Action.key, Bot_Action.secret, trustedLimitUSD, prise), "#0.00");
        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(quantiti), String.valueOf(orderPrise), "buy");
        return e;
    }

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
            System.out.println(Arrays.toString(newOrdd));
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

    public static void main(String[] args) throws InterruptedException, SocketTimeoutException {
        System.out.println("st");
        double prise = Calculation.getFormatPrise(Double.parseDouble(Modules.getPrise(Bot_Action.key, Bot_Action.secret, Bot_Action.pair).get("1").toString()), "#0.0000");
        double trustedLimitUSD = Double.parseDouble((String) Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.getValent(), 5/*Bot_Action.getTrustLimit()*/, String.valueOf(prise)).get("trustUsd"));
        double trustedLimitETH = Double.parseDouble((String) Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.getValent(), 5/*Bot_Action.getTrustLimit()*/, String.valueOf(prise)).get("trustEth"));
        double persProfit = 0.5;/*Bot_Action.getPrsProfit();*/ // НУЖНО ОБРАБОТАТЬ!!!!!!!!!!!!!!!111
        double orderLifeTime = Bot_Action.getorderLifeTime();
        int lifeTime = 0; //Счетчик жизни
        double orderPrise = 0.0;
        boolean checkByOrBit = true; //проверка buy or sell
        double oldOrderPrise = 0.0;
        double quantiti = trustedLimitUSD / prise;
        String chekByOrSell = Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.getValent(), trustedLimitUSD, String.valueOf(prise)).get("chekVal").toString();

        OrderMonitor or = new OrderMonitor();
        or.start();
       String tt = OrderTupes.sellOrder(6000, 2);
        System.out.println(tt);
       tt = tt.replaceAll("\"", ""); 
       tt = tt.substring(tt.indexOf("order_id:"), tt.indexOf("order_id:") + 18);
       String ee = OrderTupes.sellOrder(5000, 2);
        System.out.println(ee);
       ee = ee.replaceAll("\"", ""); 
       ee = ee.substring(ee.indexOf("order_id:"), ee.indexOf("order_id:") + 18);
        while (true) {
            Thread.sleep(1000);
            if(OrderMonitor.getMonitRes().toString().equalsIgnoreCase(tt)){
                System.out.println("Сработал");
            System.out.println(OrderMonitor.getMonitRes());
            }
            if(OrderMonitor.getMonitRes().toString().equalsIgnoreCase(ee)){
                System.out.println("Сработал");
            System.out.println(OrderMonitor.getMonitRes());
            }

            
            
        }

//        int oldNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
//        int newNumOrders = 0;
//        String newOrd = "";
//        String oldOrd = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("order").toString();;
//        while (true) {
//            newNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
//            newOrd = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("order").toString();
//            if (newNumOrders < oldNumOrders) {
//                System.out.println(getDelleteOrder(Bot_Action.key, Bot_Action.secret, newOrd, oldOrd).get("delOrd").toString());
//                oldNumOrders = newNumOrders;
//                oldOrd = newOrd;
//            }
//            if (newNumOrders > oldNumOrders) {
//                oldOrd = newOrd;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(OrderMonitor.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            oldNumOrders = newNumOrders;
//        }
//                    Проверка какой ордер создать вначале
///////////////////////////////////////////////////////////////////////////////////////////////////////////
//        if (chekByOrSell.equalsIgnoreCase("sell")) {
//            //Здесь нужно создать ордер на продажу
//            orderPrise = Calculation.getFormatPrise(Calculation.getOrderSellPrise(Bot_Action.pair, Bot_Action.getOrderCount(), persProfit, trustedLimitUSD, Bot_Action.key, Bot_Action.secret, Bot_Action.getAverageOrCurent(), prise), "#0.00");
//            oldOrderPrise = orderPrise;
////            String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(trustedLimitETH), String.valueOf(orderPrise), "sell");
////            System.out.println(e);
//            System.out.println("Cоздание ордера на ПРОДАЖУ " + orderPrise + "\n");
//            checkByOrBit = true;
//        } else if (chekByOrSell.equalsIgnoreCase("buy")) {
//            //Здесь нужно создать ордер на покупку
//            orderPrise = Calculation.getFormatPrise(Calculation.getOrderBuyPrise(Bot_Action.pair, persProfit, Bot_Action.key, Bot_Action.secret, trustedLimitUSD, prise), "#0.00");
//            oldOrderPrise = orderPrise;
////            String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(quantiti), String.valueOf(orderPrise), "buy");
////            System.out.println(e);
//            System.out.println("Cоздание ордера на ПОКУПКУ " + orderPrise + "\n");
//            checkByOrBit = false;
//        }
//        System.out.println("oldOrderPrise " + oldOrderPrise);
//        System.out.println("Ордер создан вхождение в цикл");
    }
}
