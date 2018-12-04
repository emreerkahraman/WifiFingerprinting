package com.example.emre.wififingerprinting;

import android.util.Log;

public class Distance {

    public double calcDistance(int signalLevel){
        double rssiInOneMeter=-30.0;
        double n=2.3;
        return Math.pow(10,(rssiInOneMeter-signalLevel)/10*n);
    }

    public double calcDistance2(int signalLevel,int freq){

        int rssiAtZero=-30;
        int fadeMargin=10;
        double n=2.3;

        return Math.pow(10,(rssiAtZero-fadeMargin-signalLevel-10*n*Math.log10(freq)+30*n-32.44)/10*n);
    }

    public double calcDistance3(int signalLevel){
        double rssiInOneMeter=-45.0;

        return 0.42093*Math.pow(signalLevel/rssiInOneMeter,6.9476)+0.54992;
    }

    public double calcDistance4(int signalLevel){
        double rssiInOneMeter=-30.0;
        double n=4.0;
        double standartdev= 8.5;
        return Math.pow(10,-(-signalLevel-rssiInOneMeter-standartdev)/10*n);
    }

    public double calcDistance5(int signalLevel,int freq){
        Log.i("info",""+signalLevel+" "+freq);
        return Math.pow(10,((27.55-(20*Math.log10(freq))-signalLevel)/20));

    }
}
