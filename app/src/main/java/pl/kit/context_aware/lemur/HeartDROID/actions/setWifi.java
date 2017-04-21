package pl.kit.context_aware.lemur.HeartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.ConnectionManager;

/**
 * Created by Krzysiek on 2017-01-13.
 */

public class setWifi implements Action {
    @Override
    /**
     * Action connected with setting wifi
     */
    public void execute(State state) {
        String argument = String.valueOf(state.getValueOfAttribute("wifi"));
        switch (argument){
            case "on":
                ConnectionManager.turnOnWiFi(Inference.getmContext());
                break;
            case "off":
                ConnectionManager.turnOffWiFi(Inference.getmContext());
                break;
        }

    }

}
