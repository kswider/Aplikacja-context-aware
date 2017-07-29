package pl.kit.context_aware.lemur.heartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.heartDROID.Inference;
import pl.kit.context_aware.lemur.phoneActions.RingModes;

/**
 * Created by Krzysiek on 2016-12-27.
 */

public class setSound implements Action {

    @Override
    /**
     * Action connected with setting sounds
     */
    public void execute(State state) {
        String argument = state.getValueOfAttribute("sound").toString();
        switch (argument){
            case "on":
                RingModes.normalMode(Inference.getmContext());
                break;
            case "off":
                RingModes.silentMode(Inference.getmContext());
                break;
            case "vibration":
                RingModes.vibrationsMode(Inference.getmContext());
                break;
        }

    }

}