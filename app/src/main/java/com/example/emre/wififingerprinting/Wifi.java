package com.example.emre.wififingerprinting;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties

public class Wifi {

    private String SSID;
    private String Mac;
    private int RSSI;



    public Wifi(){}




    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }


}
