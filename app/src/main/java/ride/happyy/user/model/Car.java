package ride.happyy.user.model;

import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitute;
    @SerializedName("car_type")
    private int car_type;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitute() {
        return longitute;
    }

    public int getCar_type() {
        return car_type;
    }
}
