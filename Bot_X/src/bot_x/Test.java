/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import java.util.HashMap;
import java.util.Stack;

/**
 *
 * @author Alex
 */
public class Test {

    public static void main(String[] args) {
        String key = "K-91fcc80c5c4263e7c61635629a3c42eaf331ce88";
        String secret = "S-8b1012889c95bb34db05cf85889f3086c21108f0";
        String valent = "ETH_USD";
        Exmo e = new Exmo(key, secret);
        String result = e.Request("order_book", new HashMap<String, String>() {
            {
                put("pair", valent);
                put("limit", "10");
            }
        });

        HashMap<String, String> statCup = new HashMap<>(); // Возвращает значения согласно ключа (ask_quantity, ask_top...)
        Stack<String> stHashKey = new Stack<>();
        Stack<String> stHashVal = new Stack<>();

        result = result.replaceAll("ETH_USD", "").replaceAll("\"", "").replaceAll("}", "").replaceAll("]", "").replaceAll("\\{", "").replaceAll("ask:\\[", "rIm ask:").replaceAll("bid:\\[", "rIm bid:").replaceAll(":ask_quantity", "ask_quantity");
        System.out.println(result);
        String[] rim = result.split("rIm");

        String[] rimStats = rim[0].split(",");
        for (int i = 0; i < rimStats.length; i++) {
            String[] rimS = rimStats[i].split(":");
            stHashKey.push(rimS[0]);
            stHashVal.push(rimS[1]);
        }

        for (int i = 0; i < rimStats.length; i++) {
            statCup.put(stHashKey.pop(), stHashVal.pop());
        }

        String ask = rim[1].replaceAll("ask:\\[", "");
        String bid = rim[2].replaceAll("bid:\\[", "");

        statCup.put("ask", ask);
        statCup.put("bid", bid);
        Double pr = 0.0;
        double dd = 0.0;
        String[] s = statCup.get("ask").split(",\\[");
        for (int i = 0; i < s.length; i++) {
            String[] d = s[0].split(",");
            dd = Double.parseDouble(d[0]);
            String[] string = s[i].split(",");
            pr += Double.parseDouble(string[0]);
        }
        double eth = 0.04395201;
        
        
        
        double p = 0.5;
        double par = dd * p /100;
        double average  = pr / 10 + par + 1.004;
        double prozent = dd + par + 1.004;
        System.out.println(ask);
        System.out.println(statCup.get("ask"));
        System.out.println(pr);
        System.out.println("Цена по среднему значению " + average );
        System.out.println(average * 0.04395201);
        
        
        
        
        
        
        
        
        
        
        
        
        System.out.println("Цена по процентному значению 0,02%   " + prozent);
        System.out.println(prozent * 0.04395201);

//        System.out.println(rim[1]);
//        System.out.println(rim[2]);
//        System.out.println(bid);
//        System.out.println(statCup.get("ask_quantity"));
//        System.out.println(statCup.get("ask_amount"));
//        System.out.println(statCup.get("ask_top"));
//        System.out.println(statCup.get("bid_quantity"));
//        System.out.println(statCup.get("bid_amount"));
//        System.out.println(statCup.get("bid_top"));
    }
}
