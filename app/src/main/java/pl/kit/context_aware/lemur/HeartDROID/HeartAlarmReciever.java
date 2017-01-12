package pl.kit.context_aware.lemur.HeartDROID;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;

/**
 * Created by Tomek on 2017-01-12.
 */

public class HeartAlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Inference inference = new Inference(context);
        for(String scriptName : FilesOperations.getAllModelNames(context)) {
            inference.runInference(scriptName);
        }

        Toast.makeText(context, "Heart run!", Toast.LENGTH_LONG).show();

        Calendar c = Calendar.getInstance();
        Long time = c.getTimeInMillis()+60*1000;

        Intent intentAlarm = new Intent(context, HeartAlarmReciever.class);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


        alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(context,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(context, "Alarm Scheduled for one minute", Toast.LENGTH_LONG).show();
    }
}
