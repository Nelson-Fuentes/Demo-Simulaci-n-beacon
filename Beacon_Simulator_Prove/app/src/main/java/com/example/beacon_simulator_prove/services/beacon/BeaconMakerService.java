package com.example.beacon_simulator_prove.services.beacon;

import org.altbeacon.beacon.Beacon;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BeaconMakerService {

    public static final int MANUFACTURER_ALT_BEACON = 0x0118;
    public static final int MANUFACTURER_UUID_EDDYSTONE = 65194;


    public BeaconMakerService(){

    }

    public Beacon makeBeacon(String uuid, String id2, String id3, int manufacturer, int txPower, List<Long> dataFields){

        Beacon beacon = new Beacon.Builder()
                .setId1(uuid)
                .setId2(id2)
                .setId3(id3)
                .setManufacturer(manufacturer)
                .setTxPower(txPower)
                .setDataFields(dataFields)
                .build();
        return beacon;


    }

    public Beacon makeAltBeacon(String uuid, String id2, String id3, int txPower, List<Long> dataFields){
        return this.makeBeacon(uuid, id2, id3, MANUFACTURER_ALT_BEACON, txPower, dataFields);
    }

    public Beacon makeUserBeacon(String dni){
        return this.makeAltBeacon(UUID.nameUUIDFromBytes(dni.getBytes()).toString(), "", "", -69, Arrays.asList(new Long[] {0l}));
    }

}
