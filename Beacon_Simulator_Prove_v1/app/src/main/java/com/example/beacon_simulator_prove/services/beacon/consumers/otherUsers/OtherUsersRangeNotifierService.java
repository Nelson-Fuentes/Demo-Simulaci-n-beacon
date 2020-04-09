package com.example.beacon_simulator_prove.services.beacon.consumers.otherUsers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import com.example.beacon_simulator_prove.adapters.recyclerView.BeaconAdapter;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;

public class OtherUsersRangeNotifierService implements RangeNotifier {

    public static final String TAG = "OTHER USERS RANGE NOTIFIER SERVICE";


    private BeaconAdapter beaconAdapter;
    private Context context;

    public OtherUsersRangeNotifierService(BeaconAdapter beaconAdapter, Context context){
        this.beaconAdapter = beaconAdapter;
        this.context = context;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        ArrayList<Beacon> dataset = new ArrayList<Beacon>();
        for (Beacon beacon: collection){
            dataset.add(beacon);
            Log.i(TAG, "The " +  beacon.getId1() + " beacon has been detected");
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
        beaconAdapter.setDataset(dataset);
    }
}
