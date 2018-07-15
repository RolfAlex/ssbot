/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import java.net.SocketTimeoutException;

/**
 *
 * @author alex
 */
public class OrderTupes {

    public static String sellOrder(double prise, double persenrProf) {
        double orderPrise = Calculation.getFormatPrise(Calculation.getOldOrderSellPrise(Bot_Action.pair, Bot_Action.getOrderCount(), persenrProf, Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, Bot_Action.getTrustLimit(), String.valueOf(prise)).get("trustUsd").toString()), Bot_Action.key, Bot_Action.secret, Bot_Action.getAverageOrCurent(), prise), "#0.00");
        String trustedLimitETH = Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, Bot_Action.getTrustLimit(), String.valueOf(prise)).get("trustEth").toString();
        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(trustedLimitETH), String.valueOf(orderPrise), "sell");
        return e.concat("tupe sell " + " orderPrise " + String.valueOf(orderPrise));
    }
//    public static String sellOrder(double prise, double persenrProf) {
//        double orderPrise = Calculation.getFormatPrise(Calculation.getOldOrderSellPrise(Bot_Action.pair, Bot_Action.getOrderCount(), 3, Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, 200, String.valueOf(prise)).get("trustUsd").toString()), Bot_Action.key, Bot_Action.secret, Bot_Action.getAverageOrCurent(), prise), "#0.00");
//        String trustedLimitETH = Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, 200, String.valueOf(prise)).get("trustEth").toString();
//        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(trustedLimitETH), String.valueOf(orderPrise), "sell");
//        return e.concat("tupe sell "+" orderPrise " + String.valueOf(orderPrise));
//    }

    public static String buyOrder(double prise, double persenrProf) throws InterruptedException, SocketTimeoutException {
        double orderPrise = Calculation.getFormatPrise(Calculation.getOldOrderBuyPrise(Bot_Action.pair, persenrProf, Bot_Action.key, Bot_Action.secret, Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, Bot_Action.getTrustLimit(), String.valueOf(prise)).get("trustUsd").toString()), prise), "#0.00");
        double quantiti = Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, Bot_Action.getTrustLimit(), String.valueOf(prise)).get("trustUsd").toString()) / prise;
        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(quantiti), String.valueOf(orderPrise), "buy");
        return e.concat("tupe buy " + " orderPrise " + String.valueOf(orderPrise));
    }

//    public static void main(String[] args) throws InterruptedException, SocketTimeoutException {
//        double prise = 6000;
////                                                                                                                          Bot_Action.getPrsProfit()                                                                                       Bot_Action.getTrustLimit();  
//        double orderPrise = Calculation.getFormatPrise(Calculation.getOldOrderSellPrise(Bot_Action.pair, Bot_Action.getOrderCount(), 3, Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, 200, String.valueOf(prise)).get("trustUsd").toString()), Bot_Action.key, Bot_Action.secret, Bot_Action.getAverageOrCurent(), prise), "#0.00");
//        String trustedLimitETH = Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, 200, String.valueOf(prise)).get("trustEth").toString();
//        System.out.println(orderPrise);
//        System.out.println(trustedLimitETH);
//        String e = Modules.orderTypeCreated(Bot_Action.key, Bot_Action.secret, Bot_Action.pair, String.valueOf(trustedLimitETH), String.valueOf(orderPrise), "sell");
//        System.out.println(e);
//                                                                                          Bot_Action.getPrsProfit()                                                                                                                   Bot_Action.getTrustLimit();  
//        double orderPrise = Calculation.getFormatPrise(Calculation.getOldOrderBuyPrise(Bot_Action.pair, 3, Bot_Action.key, Bot_Action.secret, Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, 19, String.valueOf(prise)).get("trustUsd").toString()), prise), "#0.00");
//        double quantiti = Double.parseDouble(Calculation.getChekTrustBalans(Bot_Action.key, Bot_Action.secret, Bot_Action.valent, 19, String.valueOf(prise)).get("trustUsd").toString()) / prise;
//        System.out.println("orderPrise " + orderPrise);
//        System.out.println("quantiti " + quantiti);
//        String e = Modules.orderTypeCreated(key, secret, Bot_Action.pair, String.valueOf(quantiti), String.valueOf(orderPrise), "buy");
//    }
}
