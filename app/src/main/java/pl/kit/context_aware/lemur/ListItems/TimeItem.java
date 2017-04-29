package pl.kit.context_aware.lemur.ListItems;

import java.sql.Time;

/**
 * Created by Tomek on 2017-04-22.
 */

public class TimeItem {
    private int hours;
    private  int minutes;
    private int hoursEnd;
    private  int minutesEnd;

    public boolean isIntervalType(){
        if((hours == hoursEnd)&&(minutes == minutesEnd)){
            return false;
        }
        return true;
    }

    public boolean isRightIntervalType(){
        if(hours < hoursEnd) return true;
        else if((hours == hoursEnd) && (minutes < minutesEnd)) return true;
        return false;
    }

    public TimeItem(TimeItem t2) {
        this.hours = t2.getHours();
        this.minutes = t2.getMinutes();
        this.hoursEnd = t2.getHoursEnd();
        this.minutesEnd = t2.getMinutesEnd();
    }

    public void setHoursEnd(int hours_end) {
        this.hoursEnd = hours_end;
    }

    public void setMinutesEnd(int minutes_end) {
        this.minutesEnd = minutes_end;
    }

    public int getHoursEnd() {
        return hoursEnd;
    }

    public TimeItem(int hours, int minutes, int hoursEnd, int minutesEnd) {
        this.hours = hours;
        this.minutes = minutes;
        this.hoursEnd = hoursEnd;
        this.minutesEnd = minutesEnd;
    }

    public int getMinutesEnd() {

        return minutesEnd;
    }

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
