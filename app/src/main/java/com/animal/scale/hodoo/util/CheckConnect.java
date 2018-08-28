package com.animal.scale.hodoo.util;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by silve on 2018-04-26.
 */

public class CheckConnect extends Thread {
    private boolean success;
    private String host;

    public CheckConnect(String host){
        this.host = host;
    }

    @Override
    public void run() {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)new URL(host).openConnection();
            conn.setRequestProperty("User-Agent","Android");
            conn.setConnectTimeout(1000);
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode == 204) success = true;
            else success = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        if(conn != null){
            conn.disconnect();
        }
    }

    public boolean isSuccess(){
        return success;
    }

}
