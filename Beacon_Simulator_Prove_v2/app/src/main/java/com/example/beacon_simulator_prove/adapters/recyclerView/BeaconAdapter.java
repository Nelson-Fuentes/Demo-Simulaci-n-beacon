package com.example.beacon_simulator_prove.adapters.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beacon_simulator_prove.R;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;

public class BeaconAdapter extends RecyclerView.Adapter<BeaconAdapter.ViewHolder> {
    private ArrayList<Beacon> dataset;



    public BeaconAdapter(){
        this.dataset = new ArrayList<Beacon>();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beacon_detected, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Beacon beacon = dataset.get(position);
        holder.uuid_beacon.setText(beacon.getId1().toString());
        holder.distance_beacon.setText(beacon.getDistance()+" m");

    }

    public void setDataset(ArrayList<Beacon> dataset){
        this.dataset = dataset;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView uuid_beacon;
        private TextView distance_beacon;

        public ViewHolder(View itemView) {
            super(itemView);

            uuid_beacon = (TextView) itemView.findViewById(R.id.uuid_detected);
            distance_beacon = (TextView) itemView.findViewById(R.id.distance_beacon_detected);
        }
    }
}
