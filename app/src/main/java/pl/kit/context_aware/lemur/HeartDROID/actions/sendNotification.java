package pl.kit.context_aware.lemur.HeartDROID.actions;

import java.util.LinkedList;
import java.util.Random;

import heart.Action;
import heart.State;
import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.PhoneActions.SendNotification;
import pl.kit.context_aware.lemur.Readers.ReadTime;


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
        double hour = ReadTime.ReadHour();
        double minute = ReadTime.ReadMinute();
        double time = hour + (minute/60);
        LinkedList<String> notification = FilesOperations.loadNotification(Inference.getmContext(),Double.toString(time));
        String title = notification.pop();
        String message = notification.pop();
        SendNotification.sendNotification(Inference.getmContext(),generator.nextInt(1000),title,message);
    }
}

