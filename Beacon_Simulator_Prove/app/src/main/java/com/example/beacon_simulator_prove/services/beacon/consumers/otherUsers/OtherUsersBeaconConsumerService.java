package com.example.beacon_simulator_prove.services.beacon.consumers.otherUsers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Adapter;

import com.example.beacon_simulator_prove.adapters.recyclerView.BeaconAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

public class OtherUsersBeaconConsumerService implements BeaconConsumer {

    public static  final String TAG = "OTHER USERS BEACON CONSUMER SERVICE";


    private Context context;
    private BeaconAdapter beaconAdapter;
    private BeaconManager beaconManager;

    public  OtherUsersBeaconConsumerService(Context context, BeaconManager beaconManager, BeaconAdapter beaconAdapter){
        this.context = context;
        this.beaconAdapter = beaconAdapter;
        this.beaconManager = beaconManager;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBeaconServiceConnect() {
        try {
            this.beaconManager.removeAllRangeNotifiers();
            this.beaconManager.addRangeNotifier(new OtherUsersRangeNotifierService(this.beaconAdapter, context));
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
