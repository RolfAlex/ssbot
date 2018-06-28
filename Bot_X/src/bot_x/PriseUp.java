/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import static bot_x.Calculation.getFormatPrise;

/**
 *
 * @author Alex
 */
 class PriseUp{
    
     public void getSituation(){
         System.out.println("up");
        String key = Bot_Action.key;
        String secret = Bot_Action.secret;
        String valent = Bot_Action.getPair();
        String valentName = "ETH";
        double trustedLimUsd = 6;

        int countOrders = Integer.parseInt(Modules.getUserOpenOrders(key, secret).get("num").toString());
        
        double oldPise = 458.71;
//        System.out.println(countOrders);
        double prise = getFormatPrise(Double.valueOf(Modules.getPrise(key, secret, valent).get("1").toString()), "#0.00");
//        System.out.println(prise);
        if (prise > (oldPise + 1)) {
            System.out.println("Цена идет вверх");
        } 
    }
}
