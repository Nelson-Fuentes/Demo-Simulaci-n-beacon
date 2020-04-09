package com.example.beacon_simulator_prove.services.beacon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;

import com.example.beacon_simulator_prove.adapters.recyclerView.BeaconAdapter;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

public class BeaconSearchService implements BeaconConsumer {

    public static  final String TAG = "BEACON SEARCH SERVICE";


    private Context context;
    private BeaconManager beaconManager;
    private RangeNotifier rangeNotifier;

    public BeaconSearchService(Context context, BeaconManager beaconManager, RangeNotifier rangeNotifier){
        this.context = context;
        this.beaconManager = beaconManager;
        this.rangeNotifier = rangeNotifier;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBeaconServiceConnect() {
        try {

            this.beaconManager.removeAllRangeNotifiers();
            this.beaconManager.addRangeNotifier(this.rangeNotifier);
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {
            Log.w(TAG, "error: " + e.getMessage());
        }
    }

    @Override
    public Context getApplicationContext() {
        return this.context;
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        this.context.unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return this.context.bindService(intent, serviceConnection, i);
    }
}
