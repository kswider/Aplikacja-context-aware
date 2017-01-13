package pl.kit.context_aware.lemur.HeartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.HeartDROID.Inference;


/**
 * Created by Krzysiek on 2017-01-13.
 */

public class sendMessage implements Action {
    @Override
    public void execute(State state) {
        String argument = String.valueOf(state.getValueOfAttribute("message"));
        switch (argument){
            case "notification":
                //TODO on Android
            case "sms":
                //TODO on Android
        }
    }
}
