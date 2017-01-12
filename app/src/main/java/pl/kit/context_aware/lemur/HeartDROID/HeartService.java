package pl.kit.context_aware.lemur.HeartDROID;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;

/**
 * Created by Tomek on 12.01.2017.
 */

public class HeartService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Inference inference = new Inference(this.getApplicationContext());
        for(String scriptName : FilesOperations.getAllModelNames(this.getApplicationContext())) {
            inference.runInference(scriptName);
        }
        Log.i("Service","Start");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Service","Stop");
    }
}
