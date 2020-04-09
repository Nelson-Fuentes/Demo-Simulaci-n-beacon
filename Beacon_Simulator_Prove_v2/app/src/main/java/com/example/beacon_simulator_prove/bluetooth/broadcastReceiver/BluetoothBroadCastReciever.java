package com.example.beacon_simulator_prove.bluetooth.broadcastReceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.beacon_simulator_prove.MainActivity;
import com.example.beacon_simulator_prove.exceptions.BluetoothDisabledException;

public class BluetoothBroadCastReciever extends BroadcastReceiver {

    private MainActivity mainActivity;

    public BluetoothBroadCastReciever(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF: {
                    BluetoothDisabledException.enableBluetooth(mainActivity);
                    break;
                }
                case BluetoothAdapter.STATE_ON: {
                    mainActivity.go();
                    break;
                }
                default:
                    break;
            }
        }
    }
}
