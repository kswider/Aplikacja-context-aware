package com.example.tomek.etapi;
import java.util.Calendar;

/**
 * Created by Tomek on 16.12.2016.
 */

public class ReadTime {
    //Zwraca pełną aktualną datę
    public static String ReadFullTime(){
        Calendar c = Calendar.getInstance();
        return c.getTime().toString();
    }

    //Zwraca aktualną godzinę w postaci HH:MM
    public static String ReadHour(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
    }

    //Zwraca sktualną datę w formacie DD.MM.YYYY
    public static String ReadDate(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH ) + "." + c.get(Calendar.YEAR);
    }

    //Zwraca liczbę z zakresu 1-7 odpowiadającą dniu tygodnia (pon = 1)
    public static int ReadDay(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
