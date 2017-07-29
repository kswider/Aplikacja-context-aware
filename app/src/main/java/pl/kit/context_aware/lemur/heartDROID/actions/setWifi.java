package pl.kit.context_aware.lemur.heartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.heartDROID.Inference;
import pl.kit.context_aware.lemur.phoneActions.ConnectionManager;

/**
 * Created by Krzysiek on 2017-01-13.
 */

public class setWifi implements Action {
    @Override
    /**
     * Action connected with setting wifi
     */
    public void execute(State state) {
        String argument = state.getValueOfAttribute("wifi").toString();
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
