package pl.kit.context_aware.lemur.HeartDROID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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
    }
}
