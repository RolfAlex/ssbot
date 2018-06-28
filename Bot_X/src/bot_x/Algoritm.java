/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import java.net.SocketTimeoutException;
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
        HashMap<String, String> userOrders = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret);

        int oldNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
        int newNumOrders = 0;
        while (true) {
            userOrders = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret);
            newNumOrders = Integer.valueOf(userOrders.get("num").toString());
            if (newNumOrders < oldNumOrders) {
                System.out.println("сработал ордер");

            }
            Thread.sleep(1000);

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
