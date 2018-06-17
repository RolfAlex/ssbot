package bot_x;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

public class Calculation {

    public static Double getOrderAskPrise(String pair, int limit, double persProfit, double trustedLimit, String key, String secret, boolean checkPrise, double prise) {
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

    public static double getFormatPrise(double prise, String length) {
        String p = new DecimalFormat(length).format(prise).replace(",", ".");
        double formPrise = Double.parseDouble(p);
        return formPrise;
    }

    public static HashMap getChekTrustBalans(String key, String secret, String valentName, double trustedLimUsd, String curPrise) {
        HashMap<String, String> balState = new HashMap<String, String>();
        Modules m = new Modules();
        double balInEth = Double.parseDouble(curPrise) * (double) m.getConfBallans(key, secret, valentName).get("val");
        double trustBalEth = trustedLimUsd / Double.parseDouble(curPrise);
        balState.put("trustEth", String.valueOf(getFormatPrise(trustBalEth, "#0.00000000")));
        balState.put("trustUsd", String.valueOf(trustedLimUsd));
        double balInUsd = (double) Modules.getConfBallans(key, secret, valentName).get("usd");
        if (trustedLimUsd > balInUsd && trustedLimUsd > balInEth) {
            balState.put("chekVal", "noMoney");
        } else {
            if (trustedLimUsd <= balInUsd) {
                balState.put("chekVal", "buy");
            }
            if (trustedLimUsd <= balInEth) {

                balState.put("chekVal", "sell");
            }
        }
        return balState;
    }

    public static void main(String[] args) {
        String key = "K-91fcc80c5c4263e7c61635629a3c42eaf331ce88";
        String secret = "S-8b1012889c95bb34db05cf85889f3086c21108f0";
        String valent = Bot_Action.getPair();
        String valentName = "ETH";
        double trustedLimUsd = 5;
        Modules mod = new Modules();
        String curPr = mod.getPrise(key, secret, valent).get("1").toString();
        System.out.println(getChekTrustBalans(key, secret, valentName, trustedLimUsd, curPr).get("chekVal").toString());
//        System.out.println(getChekTrustBalans(key, secret, valent, valentName, trustedLimUsd, curPr).get("trustEth"));
//        System.out.println(getChekTrustBalans(key, secret, valent, valentName, trustedLimUsd, curPr).get("trustUsd"));
//        System.out.println(m.getConfBallans(key, secret, "ETH").get("val"));
//        System.out.println(mod.getConfBallans(key, secret, "ETH").get("usd"));
//        
//        System.out.println("Val "+configBal.get("val"));
//        System.out.println("USD "+configBal.get("usd"));
//        System.out.println(mod.getUserBalansInfo(key, secret).get("balans"));
    }

    static double getChekTrustBalans(String key, String secret, String valent, double trustLimit, double prise) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
