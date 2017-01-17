package pl.kit.context_aware.lemur.HeartDROID.actions;

import java.util.Random;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.SendNotification;
import pl.kit.context_aware.lemur.R;


/**
 * Created by Krzysiek on 2017-01-13.
 */

public class SendMessage implements Action {
    @Override
    public void execute(State state) {
        String argument = String.valueOf(state.getValueOfAttribute("message"));
        switch (argument){
            case "notification":
                Random generator = new Random();
                SendNotification.sendNotification(Inference.getmContext(),generator.nextInt(1000),Inference.getmContext().getResources().getString(R.string.default_message_main),Inference.getmContext().getResources().getString(R.string.default_message_sub));
                break;
            case "sms":
                //TODO on Android
        }
    }
}
