/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashMap;

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
//            System.out.println(newOrd.length() + "----" + oldOrd.length());
            String[] newOrdd = newOrd.replace(" ", "").split("order");
            String[] oldOrdd = oldOrd.replace(" ", "").split("order");
            System.out.println(Arrays.toString(newOrdd));
            System.out.println(Arrays.toString(oldOrdd));
            int it = 0;
            for (int i = 0; i < oldOrdd.length; i++) {
                for (int j = 0; j < newOrdd.length; j++) {
                    System.out.println(oldOrdd[i] + "----" + newOrdd[j]);
                    if (oldOrdd[i].equalsIgnoreCase(newOrdd[j])) {

                        it++;
                    }
                }
//                System.out.println(it);
                if (it == 0) {
                    tm = oldOrdd[i];
                    System.out.println(oldOrdd[i] + "ttttttttttt");
//                    System.out.println(tm + "2");
                    oldOrdd[i] = oldOrdd[i].substring(oldOrdd[i].indexOf("type"), oldOrdd[i].indexOf("type") + 8);
                    oldOrdd[i] = oldOrdd[i].concat(" " + tm.substring(tm.indexOf("price"), tm.length()));
                    tmp.put("delOrd", oldOrdd[i]);
                }
                it = 0;
            }
        } else {
            tm = oldOrd;
//            System.out.println(oldOrd + "1");
//            System.out.println(oldOrd + "2");
            oldOrd = oldOrd.substring(oldOrd.indexOf("type"), oldOrd.indexOf("type") + 8);
            oldOrd = oldOrd.concat(" " + tm.substring(tm.indexOf("price"), tm.length()));
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

        System.out.println("------------------------");
        System.out.println("oldOrderPrise " + oldOrderPrise);
        System.out.println("profit " + persProfit);
        System.out.println("usd " + trustedLimitUSD);
        System.out.println("eth " + trustedLimitETH);
        System.out.println("By оr Sell " + chekByOrSell);
        System.out.println("LifeTime " + orderLifeTime);
        System.out.println("quantiti " + quantiti);
        System.out.println("------------------------");

        System.out.println(priseMonitor(439.6, prise));
        System.out.println(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
        int oldNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
        int newNumOrders = 0;
        String newOrd = "";
        String oldOrd = "";

        while (true) {
            newNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
            newOrd = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("order").toString();
            System.out.println("newOrd " + newOrd + " oldOrd " + oldOrd);
            System.out.println(newNumOrders + "#  =   #" + oldNumOrders);
            if (newNumOrders < oldNumOrders) {

                System.out.println("сработал ордер");
                System.out.println(newOrd + " new   old " + oldOrd);
                System.out.println(getDelleteOrder(Bot_Action.key, Bot_Action.secret, newOrd, oldOrd).get("delOrd"));
                oldNumOrders = newNumOrders;
                oldOrd = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("order").toString();
            }
            oldNumOrders = newNumOrders;

            oldOrd = newOrd;
            Thread.sleep(1000);

//            System.out.println("oldOrd " + oldOrd);
        }
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
