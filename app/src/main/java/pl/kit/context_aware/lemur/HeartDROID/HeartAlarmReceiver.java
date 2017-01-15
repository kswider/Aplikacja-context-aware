package pl.kit.context_aware.lemur.HeartDROID;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;

/**
 * Created by Tomek on 2017-01-12.
 */

public class HeartAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, new Date().toString(),
                Toast.LENGTH_SHORT).show();
        String tmp = new Date().toString();

        Log.i("App",tmp); //TODO Delete when not necessary

        Inference inference = new Inference(context);
        for(String scriptName : FilesOperations.getAllModelNames(context)) {
            inference.runInference(context.getFilesDir() + "/" + scriptName + ".hmr");
        }
    }
}
