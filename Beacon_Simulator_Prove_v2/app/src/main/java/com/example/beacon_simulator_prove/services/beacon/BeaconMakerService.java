package com.example.beacon_simulator_prove.services.beacon;

import com.example.beacon_simulator_prove.services.session.DataSession;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BeaconMakerService {

    public static final int MANUFACTURER_ALT_BEACON = 65535;
    public static final int MANUFACTURER_UUID_EDDYSTONE = 65194;


    public BeaconMakerService(){

    }

    public Beacon makeBeacon(UUID uuid, String id2, String id3, int manufacturer, int txPower, List<Long> dataFields){

        Beacon beacon = new Beacon.Builder()

                .setId1(uuid.toString())
                .setId2(id2)
                .setId3(id3)
                .setManufacturer(manufacturer)
                .setTxPower(txPower)
                .setDataFields(dataFields)
                .build();
        return beacon;


    }

    public Beacon makeAltBeacon(UUID uuid, String id2, String id3, int txPower, List<Long> dataFields){
        return this.makeBeacon(uuid, id2, id3, MANUFACTURER_ALT_BEACON, txPower, dataFields);
    }

    public Beacon makeUserBeacon(){

        UUID uuid = DataSession.uuidUser;
        String id1="0";
        String id2="0";

        if (DataSession.idLocation.length()<=5){
            id1 = DataSession.idLocation;
            id2 = "0";
        } else {
            id1 = DataSession.idLocation.substring(0, 5);
                id2=DataSession.idLocation.substring(5, DataSession.idLocation.length());
        }
        return this.makeAltBeacon(uuid, id1 , id2, -69, Arrays.asList(new Long[] {0l}));
    }
}
