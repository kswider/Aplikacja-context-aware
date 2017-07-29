package pl.kit.context_aware.lemur.heartDROID.actions;

import java.util.LinkedList;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.filesOperations.FilesOperations;
import pl.kit.context_aware.lemur.heartDROID.Inference;
import pl.kit.context_aware.lemur.phoneActions.SendSMS;

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
        LinkedList<String> smses = FilesOperations.loadSMS(Inference.getmContext(),name);
        for(int i = 0; i < smses.size()/2; ++i){ //smses contains number and message for each sms, so in fact there smses.size()/2 smses
            String number = smses.pop();
            String message = smses.pop();
            SendSMS.sendMessage(Inference.getmContext(),number,message);
        }

    }
}


