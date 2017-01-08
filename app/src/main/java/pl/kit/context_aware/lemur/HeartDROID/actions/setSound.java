package pl.kit.context_aware.lemur.HeartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.RingModes;

/**
 * Created by Krzysiek on 2016-12-27.
 */

public class setSound implements Action {

    @Override
    public void execute(State state) {
        String argument = String.valueOf(state.getValueOfAttribute("sound_settings"));
        RingModes rs = new RingModes();
        switch (argument){
            case "on":
                rs.normalMode(Inference.getmContext());
                break;
            case "off":
                rs.silentMode(Inference.getmContext());
                break;
            case "vibration":
                rs.vibrationsMode(Inference.getmContext());
                break;
        }

    }

}