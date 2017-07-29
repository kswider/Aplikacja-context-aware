package pl.kit.context_aware.lemur.phoneActions;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by Tomek on 2017-04-21.
 */

public class SendSMS {
    /**
     * method sending SMS message
     * @param mContext Context of the activity which calls the method.
     * @param phoneNo String number of message rceiver
     * @param sms String containing message
     */
    public static void sendMessage(Context mContext, String phoneNo, String sms) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
        } catch (Exception e) {
            Toast.makeText(mContext.getApplicationContext(),
                    "Cant send SMS to: "+phoneNo,
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    }
