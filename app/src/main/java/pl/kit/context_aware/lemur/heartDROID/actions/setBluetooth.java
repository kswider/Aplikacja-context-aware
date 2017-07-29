package pl.kit.context_aware.lemur.heartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.phoneActions.BluetoothManager;

/**
 * Created by Krzysiek on 2017-01-13.
 */

public class setBluetooth implements Action {
    @Override
    /**
     * Action connected with setting bluetooth
     */
    public void execute(State state) {
        String argument = state.getValueOfAttribute("bluetooth").toString();
        switch (argument){
            case "on":
                BluetoothManager.turnOnBluetooth();
                break;
            case "off":
                BluetoothManager.turnOffBluetooth();
                break;
        }

    }

}
