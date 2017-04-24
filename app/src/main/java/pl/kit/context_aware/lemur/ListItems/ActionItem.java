package pl.kit.context_aware.lemur.ListItems;

/**
 * Created by Tomek on 2017-04-24.
 */

public class ActionItem {
    private String mainText;
    private String subText;
    private  int actionType;

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
