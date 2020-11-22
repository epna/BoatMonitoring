package com.boat.boatmonitoring;

import android.content.Context;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.Date;

public  class dataNavigation {
    public static Integer delay_interval = 2000;
    public static Integer delay_demarrage = 500;
    public static Date lastupdate;
    public static String trame;
    public static float[][] dataNavigation = new float[2][30];

    public dataNavigation() {
        super ();
    }

    public static String getHotspotName(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context.getApplicationContext ().getSystemService ( Context.WIFI_SERVICE );
            WifiInfo wifiInfo;

            wifiInfo = wifiManager.getConnectionInfo ();
            if (wifiInfo.getSupplicantState () == SupplicantState.COMPLETED) {
                return wifiInfo.getSSID ();
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace ();
            return "";
        }
    }

public static Boolean goodWifi (Context context)
{
    try {

        WifiManager wifiManager = (WifiManager) context.getApplicationContext ().getSystemService ( Context.WIFI_SERVICE );
        WifiInfo wifiInfo;

        wifiInfo = wifiManager.getConnectionInfo ();
        if (wifiInfo.getSupplicantState () == SupplicantState.COMPLETED) {

            return wifiInfo.getSSID ().contains (   "SFR_ACA8") ||  wifiInfo.getSSID ().contains (  "Dodgers52");
        }
        return false;
    } catch (Exception e) {
        e.printStackTrace ();
        return false;
    }
}


}
