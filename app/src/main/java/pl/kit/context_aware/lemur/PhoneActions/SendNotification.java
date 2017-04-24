package pl.kit.context_aware.lemur.PhoneActions;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;

import pl.kit.context_aware.lemur.R;
import pl.kit.context_aware.lemur.Activities.MainActivity;

/**
 * Created by Tomek on 2017-01-07.
 */

public class SendNotification {
    /**
     * method sending notification to the action bar
     * @param mContext Context of the activity which calls the method.
     * @param id unique id number (int) of notification. Must be different for each notification!
     * @param title String containing title of Notification
     * @param text String containing longer text of Notification
     */
    public static void sendNotification(Context mContext, int id, String title, String text){
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new android.support.v4.app.NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

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

        int mId = id;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
