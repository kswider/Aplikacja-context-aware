package pl.kit.context_aware.lemur.Services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import pl.kit.context_aware.lemur.activities.MainActivity;
import pl.kit.context_aware.lemur.HeartDROID.HeartService;
import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.Readers.ReadTime;

/**
 * Created by Tomek on 2017-07-17.
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
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getText(R.string.noti_title))
                .setContentText(getText(R.string.noti_message))
                .setSmallIcon(R.drawable.ic_lemur_notify)
                .setContentIntent(pendingIntent)
                .setTicker("IDK")
                .build();

        startForeground(17, notification);

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
