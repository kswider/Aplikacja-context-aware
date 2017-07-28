package pl.kit.context_aware.lemur.PhoneActions;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Tomek on 2017-01-17.
 * Class contains methods used turning on/off bluetooth
 * @author TomaszBorowicz
 */

public class BluetoothManager {
    /**
     * Method turns bluetooth On
     */
    public static void turnOnBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.enable();
    }

    /**
     * Method turns bluetooth Off
     */
    public static void turnOffBluetooth(){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothAdapter.disable();
    }
}
