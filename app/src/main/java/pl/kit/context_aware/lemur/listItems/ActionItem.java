package pl.kit.context_aware.lemur.listItems;

/**
 * Created by Tomek on 2017-04-24.
 */

public class ActionItem {
    private String mainText;
    private String subText;
    private int actionType;

    private String notificationTitle;
    private String notificationMessage;
    private String smsPhoneNumber;
    private String smsMessage;

    public static final int ACTION_BLUETOOTH_ON = 0;
    public static final int ACTION_BLUETOOTH_OFF = 1;
    public static final int ACTION_WIFI_ON = 2;
    public static final int ACTION_WIFI_OFF = 3;
    public static final int ACTION_SOUND_ON = 5;
    public static final int ACTION_SOUND_OFF = 4;
    public static final int ACTION_VIBRATIONS_MODE = 6;
    public static final int ACTION_SEND_NOTIFICATION = 7;
    public static final int ACTION_SEND_SMS =8;

    @Override
    public int hashCode() {
        return actionType;
    }

    @Override
    public boolean equals(final Object obj){
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        ActionItem other = (ActionItem) obj;
        if(this.getActionType() == ((ActionItem) obj).getActionType()) return true;
        return false;
    }

    public ActionItem(){
        mainText = "";
        subText = "";
        actionType = -1;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public void setNotificationTitle(String notificationTitle) { this.notificationTitle = notificationTitle; }

    public void setNotificationMessage(String notificationMessage) { this.notificationMessage = notificationMessage; }

    public void setSmsPhoneNumber(String smsPhoneNumber) { this.smsPhoneNumber = smsPhoneNumber; }

    public void setSmsMessage(String smsMessage) { this.smsMessage = smsMessage; }

    public String getMainText() {
        return mainText;
    }

    public String getSubText() {
        return subText;
    }

    public int getActionType() {
        return actionType;
    }

    public String getNotificationTitle() { return notificationTitle; }

    public String getNotificationMessage() { return notificationMessage; }

    public String getSmsPhoneNumber() {
        return smsPhoneNumber;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public ActionItem(String mainText, String subText, int actionType) {

        this.mainText = mainText;
        this.subText = subText;
        this.actionType = actionType;
    }
}
