package pl.kit.context_aware.lemur.ListItems;

/**
 * Created by Tomek on 2017-04-24.
 */

public class ActionItem {
    private String mainText;
    private String subText;
    private int actionType;

    public static final int ACTION_BLUETOOTH_ON = 0;
    public static final int ACTION_BLUETOOTH_OFF = 1;
    public static final int ACTION_WIFI_ON = 2;
    public static final int ACTION_WIFI_OFF = 3;
    public static final int ACTION_SOUND_ON = 5;
    public static final int ACTION_SOUND_OFF = 4;
    public static final int ACTION_VIBRATIONS_MODE = 6;
    public static final int ACTION_SEND_NOTIFICATION = 7;
    public static final int ACTION_SEND_SMS =8;

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getMainText() {
        return mainText;
    }

    public String getSubText() {
        return subText;
    }

    public int getActionType() {
        return actionType;
    }

    public ActionItem(String mainText, String subText, int actionType) {

        this.mainText = mainText;
        this.subText = subText;
        this.actionType = actionType;
    }
}
