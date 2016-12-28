package com.example.tomek.etapi;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Vibrator;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Tomek on 16.12.2016.
 * Class contains methods that might be call due to some conditions set by the user.
 * @author TomaszBorowicz
 */

public class ResultActions {
    /**
     * method sending notification to the action bar
     * @param mContext Context of the activity which calls the method.
     */
    public void sendNotification(Context mContext){
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Wiadomość od Krzysia!")
                        .setContentText("Gdzie masz krzesło ?!");

        Intent resultIntent = new Intent(mContext, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        int mId = 5;
        mNotificationManager.notify(mId, mBuilder.build());
    }

    /**
     * Method sets phone into silent mode
     * @param mContext Context of the activity which calls the method.
     */
    public void silentMode(Context mContext){
        AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    /**
     * Method sets phone into vibrations only mode
     * @param mContext Context of the activity which calls the method.
     */
    public void vibrationsMode(Context mContext){
        AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    public void vibrationsMode(){
        AudioManager audio = (AudioManager) MyApplication.getContext().getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    /**
     * Method returns phone to previous, unmuted mode.
     * @param mContext Context of the activity which calls the method.
     */
    public void normalMode(Context mContext){
        AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }


}
