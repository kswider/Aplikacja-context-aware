package pl.kit.context_aware.lemur.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import pl.kit.context_aware.lemur.FilesOperations.FilesOperations;
import pl.kit.context_aware.lemur.HeartDROID.HeartService;
import pl.kit.context_aware.lemur.HeartDROID.Inference;
import pl.kit.context_aware.lemur.Readers.ReadTime;

/**
 * Created by Iza on 2017-07-17.
 */

public class MainForegroundService extends Service {
    private Handler handler = new Handler();
    private Context mContext;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d("Lemur",ReadTime.ReadFullTime());
            mContext.startService(new Intent(mContext, HeartService.class));
            handler.postDelayed(runnable, 60000);
        }
    };

    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        Log.d("Lemur","Starting service");
        handler.post(runnable);

        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
