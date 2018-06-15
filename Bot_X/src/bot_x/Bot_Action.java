/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot_x;

import javax.swing.SwingWorker;

public class Bot_Action {

    static String key = "";
    static String secret = "";
    static String pair = "ETH_USD";
    static String limit = "1";

    public static String getKey() {
        return key;
    }

    public static String getSecret() {
        return secret;
    }

    public static String getPair() {
        return pair;
    }

    public static String getLimit() {
        return limit;
    }
}
