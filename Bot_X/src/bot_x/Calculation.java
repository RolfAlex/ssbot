package bot_x;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Calculation {

    public static Double getOrderSellPrise(String pair, int limit, double persProfit, double trustedLimit, String key, String secret, boolean checkPrise, double prise) {
        Modules mod = new Modules();
        double orderAskPrise = 0.0;
        String[] ask_ar = mod.getOrderBook(key, secret, pair, String.valueOf(limit)).get("ask").toString().split(",\\[");
        for (String ask_ar1 : ask_ar) {
            String[] ar_1 = ask_ar1.split(",");
            orderAskPrise += Double.parseDouble(ar_1[0]);
        }
        double averagePrise = 0.0;
        if (checkPrise == true) {
            orderAskPrise = orderAskPrise;
            averagePrise = orderAskPrise / limit;
        } else if (checkPrise == false) {
            orderAskPrise = prise;
            averagePrise = orderAskPrise;
        }
//        System.out.println("полученое значение профита - " + persProfit + " Доверренный лимит - " + trustedLimit);
        double prsPrfSal = trustedLimit * persProfit / 100;
        System.out.println("prsPrfSal " + prsPrfSal);
        System.out.println(averagePrise + " + " + prsPrfSal + " * " + 1.004);
        double orderPrise = (averagePrise + prsPrfSal) * 1.004;
        return orderPrise;
    }

    public static Double getOrderBuyPrise(String pair, double persProfit, String key, String secret, double trustedLimit, double prise) throws InterruptedException, SocketTimeoutException {
        Modules mod = new Modules();
        //Вычисление средней цены на << ПОКУПКУ >> ******************************
//        System.out.println("ПОКУПКУ полученое значение профита - " + persProfit + " Доверренный лимит - " + trustedLimit);

        double prsPrfSal = trustedLimit * persProfit / 100;
        System.out.println("Profit - " + prsPrfSal);
        double orderPrise = (prise + prsPrfSal) * 1.004;
        orderPrise = orderPrise - prise;
        orderPrise = prise - orderPrise;
        return orderPrise;

    }

    public static Double getOldOrderSellPrise(String pair, int limit, double persProfit, double trustedLimit, String key, String secret, boolean checkPrise, double oldPrise) {
        Modules mod = new Modules();
        double orderAskPrise = 0.0;
        String[] ask_ar = mod.getOrderBook(key, secret, pair, String.valueOf(limit)).get("ask").toString().split(",\\[");
        for (String ask_ar1 : ask_ar) {
            String[] ar_1 = ask_ar1.split(",");
            orderAskPrise += Double.parseDouble(ar_1[0]);
        }
        double averagePrise = 0.0;
        if (checkPrise == true) {
            orderAskPrise = orderAskPrise;
            averagePrise = orderAskPrise / limit;
        } else if (checkPrise == false) {
            orderAskPrise = oldPrise;
            averagePrise = orderAskPrise;
        }
//        System.out.println("полученое значение профита - " + persProfit + " Доверренный лимит - " + trustedLimit);
        double prsPrfSal = trustedLimit * persProfit / 100;
        System.out.println("prsPrfSal " + prsPrfSal);
        System.out.println(averagePrise + " + " + prsPrfSal + " * " + 1.004);
        double orderPrise = (averagePrise + prsPrfSal) * 1.004;
        return orderPrise;
    }

    public static Double getOldOrderBuyPrise(String pair, double persProfit, String key, String secret, double trustedLimit, double oldPrise) throws InterruptedException, SocketTimeoutException {
        Modules mod = new Modules();
        //Вычисление средней цены на << ПОКУПКУ >> ******************************
        String bid_ar = Modules.getPrise(key, secret, pair).get("1").toString();
//        System.out.println("ПОКУПКУ полученое значение профита - " + persProfit + " Доверренный лимит - " + trustedLimit);

        double prsPrfSal = trustedLimit * persProfit / 100;
        System.out.println("Profit - " + prsPrfSal);
        double orderPrise = (oldPrise + prsPrfSal) * 1.004;
        orderPrise = orderPrise - oldPrise;
        orderPrise = oldPrise - orderPrise;
        return orderPrise;

    }

    public static double getFormatPrise(double prise, String length) {
        String p = new DecimalFormat(length).format(prise).replace(",", ".");
        double formPrise = Double.parseDouble(p);
        return formPrise;
    }

    public static HashMap getChekTrustBalans(String key, String secret, String valentName, double trustedLimUsd, String curPrise) {
        HashMap<String, String> balState = new HashMap<String, String>();
        Modules m = new Modules();
        double balInEth = Double.parseDouble(curPrise) * (double) m.getConfBallans(key, secret, valentName).get("val");
        double balInUsd = (double) Modules.getConfBallans(key, secret, valentName).get("usd");
        System.out.println("balInEth "+balInEth);
        System.out.println("balInUsd "+balInUsd);
        double trustBalEth = trustedLimUsd / Double.parseDouble(curPrise);
        System.out.println("trustBalEth "+trustBalEth);
        
        balState.put("trustEth", String.valueOf(getFormatPrise(trustBalEth, "#0.00000000")));
        balState.put("trustUsd", String.valueOf(trustedLimUsd));
        if (trustedLimUsd > balInUsd && trustedLimUsd > balInEth) {
            balState.put("chekVal", "noMoney");
        } else {
            if (trustedLimUsd <= balInUsd) {
                balState.put("chekVal", "buy");
            } else if (trustBalEth <= balInEth) {
                balState.put("chekVal", "sell");
            }
        }
        return balState;
    }

    public static void main(String[] args) throws InterruptedException, SocketTimeoutException {
        String key = Bot_Action.key;
        String secret = Bot_Action.secret;
        String valent = Bot_Action.getPair();
        String valentName = "ETH";
        double trustedLimUsd = 6;
        Modules mod = new Modules();
        String curPr = mod.getPrise(key, secret, valent).get("1").toString();
        System.out.println(getChekTrustBalans(key, secret, valentName, trustedLimUsd, curPr).get("chekVal").toString());
//        
//        System.out.println(getChekTrustBalans(key, secret, valentName, trustedLimUsd, curPr).get(""));
//        System.out.println(getChekTrustBalans(key, secret, valent, valentName, trustedLimUsd, curPr).get("trustEth"));
//        System.out.println(getChekTrustBalans(key, secret, valent, valentName, trustedLimUsd, curPr).get("trustUsd"));
//        System.out.println(m.getConfBallans(key, secret, "ETH").get("val"));
//        System.out.println(getOldOrderBuyPrise(Bot_Action.pair,0.5, key, secret, 6.0, 500.0));
//        System.out.println("500");
//        System.out.println(getOldOrderSellPrise(Bot_Action.pair, 6, 0.5, 6.0, key, secret, false, 500));
//        
//        System.out.println("Val "+configBal.get("val"));
//        System.out.println("USD "+configBal.get("usd"));
//        System.out.println(mod.getUserBalansInfo(key, secret).get("balans"));

    }
}
