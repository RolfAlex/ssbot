/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import static bot_x.Algoritm.getDelleteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alex
 */
public class OrderMonitor extends Thread {

    static String res = "";

    @Override
    public void run() {
        try {
            int oldNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
            int newNumOrders = 0;
            String newOrd = "";
            String oldOrd = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("order").toString();;
            while (true) {
                res = "";
                newNumOrders = Integer.valueOf(Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("num").toString());
                newOrd = Modules.getUserOpenOrders(Bot_Action.key, Bot_Action.secret).get("order").toString();
                if (newNumOrders < oldNumOrders) {
                    res = Algoritm.getDelleteOrder(Bot_Action.key, Bot_Action.secret, newOrd, oldOrd).get("delOrd").toString();
                    oldNumOrders = newNumOrders;
                    oldOrd = newOrd;
                }
                if (newNumOrders > oldNumOrders) {
                    oldOrd = newOrd;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(OrderMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
                oldNumOrders = newNumOrders;
            }
        } catch (NullPointerException e) {
            System.out.println("NULL БЛЯ");
            run();   
        }

    }

    public static String getMonitRes() {
        return res;
    }
}
