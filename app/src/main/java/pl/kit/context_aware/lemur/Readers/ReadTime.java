package pl.kit.context_aware.lemur.Readers;
import java.util.Calendar;

/**
 * Created by Tomek on 16.12.2016.
 * Class contains methods used to read time in various formats
 * @author TomaszBorowicz
 */

public class ReadTime {

    /**
     * Methods returns full time information
     * @return String with this pattern: Day Month DD HH:MM:SS GMTXXX YYYY
     */
    public static String ReadFullTime(){
        Calendar c = Calendar.getInstance();
        return c.getTime().toString();
    }

    /**
     * Method returns current hour
     * @return String with pattern HH
     */
    public static int ReadHour(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Method returns current minute
     * @return String with pattern MM
     */
    public static int ReadMinute(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MINUTE);
    }

    /**
     * Method returns current date
     * @return String with pattern YYYYMMDD
     */
    public static String ReadDate(){
        Calendar c = Calendar.getInstance();
        return String.format("%4d",c.get(Calendar.YEAR)) + String.format("%02d",c.get(Calendar.MONTH )) + String.format("%02d",c.get(Calendar.DAY_OF_MONTH));

    }

    /**
     * Method returns current day number
     * @return int number of current day in week (eg. Mon-1,Tue-2,...,Sun-7)
     */
    public static int ReadDay(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }
}
