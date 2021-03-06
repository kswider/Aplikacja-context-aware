package pl.kit.context_aware.lemur.phoneActions;

import android.content.Context;
import android.media.AudioManager;

/**
 * Created by Tomek on 16.12.2016.
 * Class contains methods used to change phone ring mode
 * @author TomaszBorowicz
 */

public class RingModes {
    /**
     * Method sets phone into silent mode
     * @param mContext Context of the activity which calls the method.
     */
    public static void silentMode(Context mContext){
        AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    /**
     * Method sets phone into vibrations only mode
     * @param mContext Context of the activity which calls the method.
     */
    public static void vibrationsMode(Context mContext){
        AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    }

    /**
     * Method returns phone to previous, unmuted mode.
     * @param mContext Context of the activity which calls the method.
     */
    public static void normalMode(Context mContext){
        AudioManager audio = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }


}

