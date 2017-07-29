package pl.kit.context_aware.lemur.heartDROID;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.kit.context_aware.lemur.filesOperations.FilesOperations;

/**
 * Created by Tomek on 2017-01-12.
 */

public class HeartAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Inference inference = new Inference(context);
        for(String scriptName : FilesOperations.getAllModelNames(context)) {
            inference.runInference(context.getFilesDir() + "/" + scriptName + ".hmr");
        }
    }
}
