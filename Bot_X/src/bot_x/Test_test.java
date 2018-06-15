/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;


/**
 *
 * @author Alex
 */
public class Test_test {

    public static Double getOrderAskPrise(String pair, String limit, double persProfit, double trustedLimit, String key, String secret, boolean checkPrise, double prise) {
        Modules mod = new Modules();
        double orderAskPrise = 0.0;
        String[] ask_ar = mod.getOrderBook(key, secret, pair, limit).get("ask").toString().split(",\\[");
        for (int i = 0; i < ask_ar.length; i++) {
            String[] ar_1 = ask_ar[i].split(",");
            orderAskPrise += Double.parseDouble(ar_1[0]);
        }
        double averagePrise = 0.0;
        if (checkPrise == true) {
            orderAskPrise = orderAskPrise;
            averagePrise = orderAskPrise / Double.parseDouble(limit);
        } else if (checkPrise == false) {
            orderAskPrise = prise;
            averagePrise = orderAskPrise;
        }
        double prsPrfSal = trustedLimit * persProfit / 100;
        System.out.println("prsPrfSal " + prsPrfSal);
        System.out.println(averagePrise + " + " + prsPrfSal + " * " + 1.004);
        double orderPrise = (averagePrise + prsPrfSal) * 1.004;
        return orderPrise;
    }

    public static Double getOrderBidPrise(String pair, double persProfit, String key, String secret, double trustedLimit) throws InterruptedException, SocketTimeoutException {
        Modules mod = new Modules();
        //Вычисление средней цены на << ПОКУПКУ >> ******************************
        String bid_ar = Modules.getPrise(key, secret, pair).get("1").toString();
        double prsPrfSal = trustedLimit * persProfit / 100;
        double orderPrise = (Double.parseDouble(bid_ar) + prsPrfSal) * 1.004;
        orderPrise = orderPrise - Double.parseDouble(bid_ar);
        orderPrise = Double.parseDouble(bid_ar) - orderPrise;
        return orderPrise;

    }

    public static double getFormatPrise(double prise) {
        String p = new DecimalFormat("#0.00").format(prise).replace(",", ".");
        double formPrise = Double.parseDouble(p);
        return formPrise;
    }

    public static void main(String[] args) throws InterruptedIOException, InterruptedException {
        String key = Bot_Action.getKey();
        String secret = Bot_Action.getSecret();
        String pair = Bot_Action.getPair();
        String limit = Bot_Action.getLimit();

        Modules mod = new Modules();

        double balans_eth = 0.04395201;
        double trustedLimit = 26.5;
        double persProfit = 0.5;
        double prise = 0.0;

        boolean checkPrise = false; // Если !true! значение цены берется по среднему значению N ордеров на покупку если !false! - по текущей цене на валюту
        prise = getFormatPrise(Double.parseDouble(mod.getPrise(key, secret, pair).get("1").toString()));

        double orderAsk = getFormatPrise(getOrderAskPrise(pair, limit, persProfit, trustedLimit, key, secret, checkPrise, prise));
        double orderBid = getFormatPrise(getOrderBidPrise(pair, persProfit, key, secret, trustedLimit));

        int lifeTime = 0;
        int orderLife = 3600;
        boolean checkByOrBit = true;
        if (checkByOrBit == false) {
            System.out.println("Цена ордера на продажу " + orderAsk);
        } else {
            System.out.println("Цена ордера на покупку " + orderBid);
        }
        while (true) {

            prise = getFormatPrise(Double.parseDouble(mod.getPrise(key, secret, pair).get("1").toString()));

            Thread.sleep(500);
            System.out.println(prise);
            //ПРОДАЖА
            if (orderAsk <= prise && checkByOrBit == false) {
                System.out.println("***********Срабатывание ордера на ПРОДАЖУ*********");
                System.out.println("Цена ордера на продажу " + orderAsk);
                System.out.println("Продано по цене " + prise);
                orderBid = getOrderBidPrise(pair, persProfit, key, secret, trustedLimit);
                System.out.println("Цена ордера на покупку " + orderBid);
                checkByOrBit = true;
            }
            //ПОКУПКА
            if (orderBid >= prise && checkByOrBit == true) {
                System.out.println("***********Срабатывание ордера на ПОКУПКУ*********");
                System.out.println("Цена ордера на покупку " + orderBid);
                System.out.println("---Куплено по цене " + prise);
                orderAsk = getOrderAskPrise(pair, limit, persProfit, trustedLimit, key, secret, checkPrise, prise);
                System.out.println("Цена ордера на продажу " + orderAsk);
                checkByOrBit = false;
            }
            //ЖИЗНЬ ОРДЕРА
            if (lifeTime > orderLife && checkByOrBit == true) {
                orderBid = getOrderBidPrise(pair, persProfit, key, secret, trustedLimit);
                System.out.println("смена ордера на продажу " + orderBid);
                lifeTime = 0;
            } else if (lifeTime > orderLife && checkByOrBit == false) {
                orderAsk = getOrderAskPrise(pair, limit, persProfit, trustedLimit, key, secret, checkPrise, prise);
                System.out.println("смена ордера на покупку " + orderAsk);
                lifeTime = 0;
            }
            lifeTime++;
        }
    }
}
