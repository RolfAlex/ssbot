package bot_x;

import java.net.SocketTimeoutException;
import java.text.DecimalFormat;

public class Calculation {

    public static Double getOrderAskPrise(String pair, String limit, double persProfit, double trustedLimit, String key, String secret, boolean checkPrise, double prise) {
        Modules mod = new Modules();
        double orderAskPrise = 0.0;
        String[] ask_ar = mod.getOrderBook(key, secret, pair, limit).get("ask").toString().split(",\\[");
        for (String ask_ar1 : ask_ar) {
            String[] ar_1 = ask_ar1.split(",");
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
    
    public static void main(String[] args) {

        Modules mod = new Modules();

    }
}
