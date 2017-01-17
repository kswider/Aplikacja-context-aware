package pl.kit.context_aware.lemur.Readers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import pl.kit.context_aware.lemur.PhoneActions.SendNotification;
import pl.kit.context_aware.lemur.R;

/**
 * Class containing functions used to reading current location
 * Created by Tomek on 23.12.2016.
 * @author TomaszBorowicz
 */

public class ReadLocation {
    private static Criteria crit;
    private static LocationManager locMan;
    private static Location loc;

    /**
     * Method returns current location in String format
     * Location is read from GPS
     * @param mContext Context of the activity which calls the method.
     * @return String in format Longitude Latitude. If dont have acces to location returns -1.00 -1.00
     */
    public static String readLocationByGPS(Context mContext) {
        return new String(readLongitudeByNetwork(mContext) + "    " + readLatitudeByNetwork(mContext));
    }

    /**
     * Method returns current location in String format
     * Location is read from Network
     * @param mContext Context of the activity which calls the method.
     * @return String in format Longitude Latitude. If dont have acces to location returns -1.00 -1.00
     */
    public static String readLocationByNetwork(Context mContext) {
        return new String(readLongitudeByGPS(mContext) + "    " + readLatitudeByGPS(mContext));
    }

    /**
     * Method returns current location in String format
     * Location is read from the best source (Network or GPS)
     * @param mContext Context of the activity which calls the method.
     * @return String in format Longitude Latitude. If dont have acces to location returns -1.00 -1.00
     */
    public static String readLocationByBest(Context mContext) {
        return new String(readLongitudeByBest(mContext) + "    " + readLatitudeByBest(mContext));
    }

    /**
     * Method returns current Longitude read from GPS
     * @param mContext Context of the activity which calls the method.
     * @return Longitude in double format. If dont have acces to location sends notification and returns -1
     */
    public static double readLongitudeByGPS(Context mContext) {
        crit = new Criteria();
        locMan = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SendNotification.sendNotification(mContext,100,mContext.getResources().getString(R.string.pl_err),mContext.getResources().getString(R.string.pl_fine_location));
            return -1.00;
        }
        loc = locMan.getLastKnownLocation(locMan.GPS_PROVIDER);
        return loc.getLongitude();
    }

    /**
     * Method returns current Latitude read from GPS
     * @param mContext Context of the activity which calls the method.
     * @return Latitude in double format If dont have acces to location sends notification and returns -1
     */
    public static double readLatitudeByGPS(Context mContext) {
        crit = new Criteria();
        locMan = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SendNotification.sendNotification(mContext,100,mContext.getResources().getString(R.string.pl_err),mContext.getResources().getString(R.string.pl_fine_location));
            return -1.00;
        }
        loc = locMan.getLastKnownLocation(locMan.GPS_PROVIDER);
        return loc.getLatitude();
    }

    /**
     * Method returns current Longitude read from Network
     * @param mContext Context of the activity which calls the method.
     * @return Longitude in double format If dont have acces to location sends notification and returns -1
     */
    public static double readLongitudeByNetwork(Context mContext) {
        crit = new Criteria();
        locMan = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SendNotification.sendNotification(mContext,100,mContext.getResources().getString(R.string.pl_err),mContext.getResources().getString(R.string.pl_fine_location));
            return -1.00;
        }
        loc = locMan.getLastKnownLocation(locMan.NETWORK_PROVIDER);
        return loc.getLongitude();
    }

    /**
     * Method returns current Latitude read from Network
     * @param mContext Context of the activity which calls the method.
     * @return Latitude in double format If dont have acces to location sends notification and returns -1
     */
    public static double readLatitudeByNetwork(Context mContext) {
        crit = new Criteria();
        locMan = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SendNotification.sendNotification(mContext,100,mContext.getResources().getString(R.string.pl_err),mContext.getResources().getString(R.string.pl_fine_location));
            return -1.00;
        }
        loc = locMan.getLastKnownLocation(locMan.NETWORK_PROVIDER);
        return loc.getLatitude();
    }

    /**
     * Method returns current Longitude read from the best source (Network ot GPS)
     * @param mContext Context of the activity which calls the method.
     * @return Longitude in double format If dont have acces to location sends notification and returns -1
     */
    public static double readLongitudeByBest(Context mContext) {
        crit = new Criteria();
        locMan = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SendNotification.sendNotification(mContext,100,mContext.getResources().getString(R.string.pl_err),mContext.getResources().getString(R.string.pl_fine_location));
            return -1.00;
        }
        loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit, true));
        return loc.getLongitude();
    }

    /**
     * Method returns current Latitude read from the best source (Network ot GPS)
     * @param mContext Context of the activity which calls the method.
     * @return Latitude in double format If dont have acces to location sends notification and returns -1
     */
    public static double readLatitudeByBest(Context mContext) {
        crit = new Criteria();
        locMan = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            SendNotification.sendNotification(mContext,100,mContext.getResources().getString(R.string.pl_err),mContext.getResources().getString(R.string.pl_fine_location));
            return -1.00;
        }
        loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit, true));
        return loc.getLatitude();
    }

}
