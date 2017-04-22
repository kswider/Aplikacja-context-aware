package pl.kit.context_aware.lemur.HeartDROID.actions;

import java.util.LinkedList;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.SendSMS;
import pl.kit.context_aware.lemur.Readers.ReadTime;

/**
 * Created by Krzysiek on 2017-04-22.
 */

public class sendSMS implements Action {
    @Override
    /**
     * Action connected with sending messages
     */
    public void execute(State state) {
        String name = state.getValueOfAttribute("smsNumber").toString();
        name = name.substring(0,name.length()-2);
        LinkedList<String> sms = FilesOperations.loadSMS(Inference.getmContext(),name);
        String number = sms.pop();
        String message = sms.pop();
        SendSMS.sendMessage(Inference.getmContext(),number,message);
    }
}


