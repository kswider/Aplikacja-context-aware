package pl.kit.context_aware.lemur.HeartDROID.actions;

import java.util.LinkedList;
import java.util.Random;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.BluetoothManager;
import pl.kit.context_aware.lemur.PhoneActions.SendNotification;


/**
 * Created by Krzysiek on 2017-01-13.
 */

public class sendNotification implements Action {
    @Override
    /**
     * Action connected with sending messages
     */
    public void execute(State state) {
        Random generator = new Random();
        String name = state.getValueOfAttribute("notificationNumber").toString();
        name = name.substring(0,name.length()-2);
        LinkedList<String> notifications = FilesOperations.loadNotification(Inference.getmContext(),name);
        for(int i=0; i < notifications.size()/2; ++i) { //notifications contains title and message for each notifications, so in fact there is notifications.size()/2 notifications
            String title = notifications.pop();
            String message = notifications.pop();
            SendNotification.sendNotification(Inference.getmContext(), generator.nextInt(1000), title, message);
        }
    }
}

