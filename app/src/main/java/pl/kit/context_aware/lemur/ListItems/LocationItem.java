package pl.kit.context_aware.lemur.ListItems;

/**
 * Created by Tomek on 2017-04-24.
 */

public class LocationItem {
    private Double latitude;
    private Double longitude;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {

        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public LocationItem(Double latitude, Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }
}
