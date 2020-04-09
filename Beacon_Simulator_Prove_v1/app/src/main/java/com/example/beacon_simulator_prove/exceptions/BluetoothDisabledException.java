package com.example.beacon_simulator_prove.exceptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class BluetoothDisabledException  extends Exception{
    public BluetoothDisabledException(){
        super("Bluetooth Disabled");
    }

    public static AlertDialog.Builder enableBluetooth(final Activity activity){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Bluetooth Desactivado");
        dialog.setMessage("Por favor activar Bluetooth para usar el aplicativo");
        dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BluetoothManager bluetoothManager;
                bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
                bluetoothManager.getAdapter().enable();
            }

        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        dialog.show();

        return dialog;
    }

}
