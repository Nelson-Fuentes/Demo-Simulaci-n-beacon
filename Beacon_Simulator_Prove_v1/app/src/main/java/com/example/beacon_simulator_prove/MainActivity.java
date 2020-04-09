package com.example.beacon_simulator_prove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.beacon_simulator_prove.adapters.recyclerView.BeaconAdapter;
import com.example.beacon_simulator_prove.bluetooth.advertiseCallbacks.BeaconTransmittorAdvertiseCallback;
import com.example.beacon_simulator_prove.bluetooth.broadcastReceiver.BluetoothBroadCastReciever;
import com.example.beacon_simulator_prove.exceptions.BluetoothDisabledException;
import com.example.beacon_simulator_prove.services.beacon.BeaconMakerService;
import com.example.beacon_simulator_prove.services.beacon.BeaconSimulatorService;
import com.example.beacon_simulator_prove.services.beacon.consumers.otherUsers.OtherUsersBeaconConsumerService;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;


public class MainActivity extends AppCompatActivity {

    public final int REQUEST_ENABLE_BLUETOOTH = 1;


    private BeaconManager beaconManager;
    private BeaconAdapter beaconAdapter;
    private RecyclerView recyclerViewBeacons;
    private BluetoothAdapter bluetoothAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(new BluetoothBroadCastReciever(this), filtro);
        this.go();
    }

    public void go() {
        this.conditions();
        this.prepare();
        this.start();
    }


    private void conditions() {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()){
            BluetoothDisabledException.enableBluetooth(this);

        }
    }

    public void prepare(){
        this.recyclerViewBeacons = (RecyclerView)findViewById(R.id.beacons_list);
        this.beaconAdapter = new BeaconAdapter();
        this.recyclerViewBeacons.setHasFixedSize(true);
        this.recyclerViewBeacons.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
        this.recyclerViewBeacons.setAdapter(beaconAdapter);
    }

    public void start() {
        this.sendBeacon();
        this.catchBeacons();
    }

    private void catchBeacons() {
            this.beaconManager = BeaconManager.getInstanceForApplication(this);
            this.beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));
            this.beaconManager.bind( new OtherUsersBeaconConsumerService(getApplicationContext(),this.beaconManager,this.beaconAdapter));
    }


    protected  void sendBeacon() {
        String dni = "72659413";
        BeaconMakerService beaconMakerService = new BeaconMakerService();
        Beacon beacon = beaconMakerService.makeUserBeacon(dni);
        BeaconParser beaconParser = new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT);
        BeaconSimulatorService beaconSimulatorService = new BeaconSimulatorService(getApplicationContext(), beaconParser);
        beaconSimulatorService.startAdvertising(beacon, new BeaconTransmittorAdvertiseCallback(beacon, (TextView)findViewById(R.id.uuid_user)));


    }


}
