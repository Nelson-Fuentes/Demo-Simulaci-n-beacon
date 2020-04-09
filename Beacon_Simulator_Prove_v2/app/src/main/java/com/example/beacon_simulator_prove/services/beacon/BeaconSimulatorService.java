package com.example.beacon_simulator_prove.services.beacon;

import android.annotation.SuppressLint;
import android.bluetooth.le.AdvertiseCallback;
import android.content.Context;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

public class BeaconSimulatorService {


    public static BeaconSimulatorService service;

    public static final String TAG = "Beacon Simulator Service";

    private BeaconTransmitter beaconTransmitter;
    private AdvertiseCallback advertiseCallback;

    @SuppressLint("LongLogTag")
    public BeaconSimulatorService(Context context, BeaconParser beaconParser){
        this.beaconTransmitter = new BeaconTransmitter(context, beaconParser);
        Log.i(TAG, "Beacon Simulator Service created");

    }

    @SuppressLint("LongLogTag")
    public void startAdvertising(Beacon beacon, AdvertiseCallback advertiseCallback){
        this.advertiseCallback = advertiseCallback;
        Log.i(TAG, "Starting Beacon Transmition");
            this.beaconTransmitter.startAdvertising(beacon, advertiseCallback);

    }

    public BeaconTransmitter getTransmittor(){
        return this.beaconTransmitter;
    }

    public void reAdvertising(Beacon beacon){
        this.beaconTransmitter.stopAdvertising();
        this.beaconTransmitter.startAdvertising(beacon, this.advertiseCallback);
    }
}
