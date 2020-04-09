package com.example.beacon_simulator_prove.bluetooth.advertiseCallbacks;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.util.Log;
import android.widget.TextView;

import com.example.beacon_simulator_prove.services.beacon.BeaconSimulatorService;

import org.altbeacon.beacon.Beacon;

public class BeaconTransmittorAdvertiseCallback extends AdvertiseCallback {

    public final String TAG = "Beacon Transmittor";
    private Beacon beacon;
    private TextView textView;
    public BeaconTransmittorAdvertiseCallback(Beacon beacon, TextView textView){
        this.beacon = beacon;
        this.textView = textView;
    }

    @Override
    public void onStartFailure(int errorCode) {
        Log.i(TAG, "Error: "  + errorCode);
    }

    @Override
    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
        Log.i(TAG, "Beacon " + this.beacon.getId1() + " transmited sucesfully." );
        this.textView.setText(this.beacon.getId1().toString());
    }
}
