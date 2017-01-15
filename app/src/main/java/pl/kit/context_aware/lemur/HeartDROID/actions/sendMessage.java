package pl.kit.context_aware.lemur.HeartDROID.actions;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.SendNotification;


/**
 * Created by Krzysiek on 2017-01-13.
 */

public class SendMessage implements Action {
    @Override
    public void execute(State state) {
        String argument = String.valueOf(state.getValueOfAttribute("message"));
        switch (argument){
            case "notification":
                SendNotification.sendNotification(Inference.getmContext(),5,"You've got new notification!","Notification message");
                break;
            case "sms":
                //TODO on Android
        }
    }
}
