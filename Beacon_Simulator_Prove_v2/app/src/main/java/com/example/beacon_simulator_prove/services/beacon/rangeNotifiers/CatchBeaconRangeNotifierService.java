package com.example.beacon_simulator_prove.services.beacon.rangeNotifiers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import com.example.beacon_simulator_prove.adapters.recyclerView.BeaconAdapter;
import com.example.beacon_simulator_prove.services.beacon.BeaconMakerService;
import com.example.beacon_simulator_prove.services.beacon.BeaconSimulatorService;
import com.example.beacon_simulator_prove.services.session.DataSession;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

public class CatchBeaconRangeNotifierService implements RangeNotifier {

    public static final String TAG = "CATCH BEACON RANGE NOTIFIER SERVICE";


    private BeaconAdapter beaconAdapter;
    private Context context;

    public CatchBeaconRangeNotifierService(BeaconAdapter beaconAdapter, Context context){
        this.beaconAdapter = beaconAdapter;
        this.context = context;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        String newLocation = "0";
        ArrayList<Beacon> dataset = new ArrayList<Beacon>();
        for (Beacon beacon: collection){
            Log.i(TAG, "The " +  beacon.getId1() + " beacon has been detected MAN: " + beacon.getManufacturer());
            switch (beacon.getManufacturer()){
                case BeaconMakerService.MANUFACTURER_ALT_BEACON:
                    if (this.validateUserBeacon(beacon)){
                        this.userAction(dataset, beacon);
                    }
                    break;
                case BeaconMakerService.MANUFACTURER_UUID_EDDYSTONE:
                    Log.i(TAG, "Location Detected");
                    newLocation =   beacon.getId2().toString().split("x")[1].substring(0, 10);

            }
        }
        beaconAdapter.setDataset(dataset);
        this.locateAction(newLocation);
    }

    @SuppressLint("LongLogTag")
    protected void locateAction(String newLocation){

        if (!newLocation.equalsIgnoreCase(DataSession.idLocation)){
            DataSession.idLocation = newLocation;
            DataSession.beaconUser = (new BeaconMakerService()).makeUserBeacon();
            BeaconSimulatorService.service.reAdvertising(DataSession.beaconUser);
            Log.i(TAG, "New Location updated");
        }

    }

    @SuppressLint("LongLogTag")
    protected void userAction(ArrayList<Beacon> dataset, Beacon beacon){
        dataset.add(beacon);
        Log.i(TAG, "User detected in " + beacon.getBluetoothName());
        if (beacon.getDistance()<2){
            try {
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(this.context, notification);
                r.play();
                vibrator.vibrate(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean validateUserBeacon(Beacon beacon){
        String id3 = beacon.getId3().toString();
        String id2 = beacon.getId2().toString();
        long idLocation = Long.parseLong(DataSession.idLocation);
        long divisor = 100000;
        return Long.parseLong(id2)==idLocation/divisor && Long.parseLong(id3) == idLocation%divisor;
    }
}
