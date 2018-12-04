package com.example.emre.wififingerprinting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView wifiRecyclerview;
    WifiAdapter adapter;
    WifiManager wifiManager;
    WifiBroadcastReceiver wifiBroadcastReceiver;
    List<ScanResult> scanResult;
    List<Wifi> wifiList;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    Button increaseX,increaseY,decreaseX,decreaseY,saveButton,scanButton,editButton;
    TextView xtextView,ytextView;
    int x=0;
    int y=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();

        database = FirebaseDatabase.getInstance();

        wifiRecyclerview = findViewById(R.id.wifiRecyclerView);
        increaseX = findViewById(R.id.increase_x);
        increaseY = findViewById(R.id.increase_y);
        decreaseX = findViewById(R.id.decrease_x);
        decreaseY = findViewById(R.id.decrease_y);
        xtextView = findViewById(R.id.xtextView);
        ytextView = findViewById(R.id.ytextView);
        saveButton = findViewById(R.id.saveButton);
        scanButton = findViewById(R.id.scanButton);
        editButton = findViewById(R.id.editButton);



        increaseX.setOnClickListener(this);
        increaseY.setOnClickListener(this);
        decreaseX.setOnClickListener(this);
        decreaseY.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        scanButton.setOnClickListener(this);
        editButton.setOnClickListener(this);



        adapter = new WifiAdapter();
        wifiList = new ArrayList<>();

        wifiRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        wifiRecyclerview.setAdapter(adapter);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        wifiBroadcastReceiver = new WifiBroadcastReceiver();
        registerReceiver( wifiBroadcastReceiver,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();



    }
    public void scanWifi(){
        Toast.makeText(this,"Scanning",Toast.LENGTH_SHORT).show();
        wifiList.clear();
        registerReceiver( wifiBroadcastReceiver ,new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();


    }
    public  void  save(){
        String  rp = x+","+y;
        myRef = database.getReference("referencepoints/"+rp);
        myRef.setValue(wifiList);
        Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
       }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.increase_x:
                x++;
                xtextView.setText("X : " +x);
                break;

            case R.id.increase_y:
                y++;
                ytextView.setText("Y : " +y);
                break;

            case R.id.decrease_x:
                x--;
                xtextView.setText("X : " +x);
                break;

            case R.id.decrease_y:
                y--;
                ytextView.setText("Y : " +y);
                break;

            case R.id.saveButton:
                save();
                break;

            case R.id.scanButton:
                scanWifi();
                break;

            case R.id.editButton:
                edit();
                break;
        }

    }

    private void edit() {


    }


    class WifiBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterReceiver(wifiBroadcastReceiver);
            scanResult = wifiManager.getScanResults();

            for (int i=0;i<scanResult.size();i++){

                Wifi wifi = new Wifi();

                wifi.setSSID(scanResult.get(i).SSID);
                wifi.setMac(scanResult.get(i).BSSID);
                wifi.setRSSI(scanResult.get(i).level);

                wifiList.add(wifi);


            }
            adapter.setData(wifiList);
            Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();

        }
    }


    public void setPermissions(){
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

    }




}
