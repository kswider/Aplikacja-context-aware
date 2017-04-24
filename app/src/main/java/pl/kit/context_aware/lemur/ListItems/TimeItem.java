package pl.kit.context_aware.lemur.ListItems;

/**
 * Created by Tomek on 2017-04-22.
 */

public class TimeItem {
    private int hours;
    private  int minutes;

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public TimeItem(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
}
