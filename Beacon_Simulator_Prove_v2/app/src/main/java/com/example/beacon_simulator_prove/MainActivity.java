package com.example.beacon_simulator_prove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.example.beacon_simulator_prove.adapters.recyclerView.BeaconAdapter;
import com.example.beacon_simulator_prove.bluetooth.advertiseCallbacks.BeaconTransmittorAdvertiseCallback;
import com.example.beacon_simulator_prove.bluetooth.broadcastReceiver.BluetoothBroadCastReciever;
import com.example.beacon_simulator_prove.exceptions.BluetoothDisabledException;
import com.example.beacon_simulator_prove.services.beacon.BeaconMakerService;
import com.example.beacon_simulator_prove.services.beacon.BeaconSimulatorService;
import com.example.beacon_simulator_prove.services.beacon.BeaconSearchService;
import com.example.beacon_simulator_prove.services.beacon.rangeNotifiers.CatchBeaconRangeNotifierService;
import com.example.beacon_simulator_prove.services.session.DataSession;

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
            this.beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
            this.beaconManager.bind( new BeaconSearchService(getApplicationContext(),this.beaconManager, new CatchBeaconRangeNotifierService(this.beaconAdapter,getApplicationContext())));
    }


    protected  void sendBeacon() {
        String dni = "72659413";
        BeaconMakerService beaconMakerService = new BeaconMakerService();
        DataSession.beaconUser = beaconMakerService.makeUserBeacon();
        BeaconParser beaconParser = new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT);
        BeaconSimulatorService.service = new BeaconSimulatorService(getApplicationContext(), beaconParser);
        BeaconSimulatorService.service.startAdvertising(DataSession.beaconUser, new BeaconTransmittorAdvertiseCallback(DataSession.beaconUser, (TextView)findViewById(R.id.uuid_user)));
    }




}
