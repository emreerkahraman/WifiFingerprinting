package com.example.emre.wififingerprinting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WifiViewHolder> {

    private List<Wifi> wifiList;

    public void  setData(List<Wifi> wifiList){
        this.wifiList = wifiList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WifiAdapter.WifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_wifi_recyclerview,parent,false);
        return new WifiViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull WifiAdapter.WifiViewHolder holder, int position) {
        Wifi wifi = wifiList.get(position);

        holder.ssidTextView.setText(wifi.getSSID());
        holder.rssiTextView.setText(String.valueOf(wifi.getRSSI()+" dBm"));
        holder.macTextView.setText(wifi.getMac());


    }

    @Override
    public int getItemCount() {
        if (wifiList != null){

            return wifiList.size();

        }

        return 0;
    }
    class WifiViewHolder extends RecyclerView.ViewHolder {
        TextView ssidTextView;
        TextView rssiTextView;
        TextView macTextView;


        public WifiViewHolder(@NonNull View itemView) {
            super(itemView);
            ssidTextView = itemView.findViewById(R.id.ssidTextView);
            rssiTextView = itemView.findViewById(R.id.rssiTextView);
            macTextView = itemView.findViewById(R.id.macTextView);


        }
    }
}
