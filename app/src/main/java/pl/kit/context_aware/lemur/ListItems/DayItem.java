package pl.kit.context_aware.lemur.ListItems;

import java.util.LinkedList;

/**
 * Created by Tomek on 2017-04-24.
 */

public class DayItem {
    private LinkedList<Integer> days = new LinkedList<>();
    private int day;
    private int month;
    private int year;

    public DayItem(int day, int month, int year, int type) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public DayItem(LinkedList<Integer> days, int type) {
        this.days = (LinkedList<Integer>) days.clone();
    }
    public void setDayOfWeek(LinkedList<Integer> days) {
        this.days = (LinkedList<Integer>) days.clone();
    }
    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
    

    public LinkedList<Integer> getDayOfWeek() {
        return days;
    }

    public int getDay() {
        return day;
    }


    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
