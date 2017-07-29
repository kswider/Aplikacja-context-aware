package pl.kit.context_aware.lemur.phoneActions;

import android.content.Context;
import android.net.wifi.WifiManager;

/**
 * Created by Tomek on 2017-01-17.
 * Class contains methods used to turn on/off WiFi
 * @author TomaszBorowicz
 */
public class ConnectionManager {

    /**
     * Method turns WiFi On
     * @param mContext Context of the activity which calls the method.
     */
    public static void turnOnWiFi(Context mContext){
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);
    }

    /**
     * Method turns WiFi Off
     * @param mContext Context of the activity which calls the method.
     */
    public static void turnOffWiFi(Context mContext){
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }
}
