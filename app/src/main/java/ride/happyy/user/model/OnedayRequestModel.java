package ride.happyy.user.model;

import com.google.gson.annotations.SerializedName;

public class OnedayRequestModel {

    @SerializedName("distance")
    String distance;
    @SerializedName("time")
    String time;
    @SerializedName("fare")
    String fare;
    @SerializedName("car_type")
    String car_type;
    @SerializedName("car_type_id")
    String car_type_id;
    @SerializedName("customer_phone")
    String customer_phone;
    @SerializedName("fcm_token")
    String fcm_token;
    @SerializedName("customer_name")
    String customer_name;
    @SerializedName("customer_photo")
    String customer_photo;
    @SerializedName("customer_location")
    String customer_location;
    @SerializedName("customer_latitude")
    String customer_latitude;
    @SerializedName("customer_longitude")
    String customer_longitude;
    @SerializedName("destination_location")
    String destination_location;
    @SerializedName("destination_latitude")
    String destination_latitude;
    @SerializedName("destination_longitude")
    String destination_longitude;
    @SerializedName("dri_phone_re_req")
    String dri_phone_re_req;

    public OnedayRequestModel() {
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_type_id() {
        return car_type_id;
    }

    public void setCar_type_id(String car_type_id) {
        this.car_type_id = car_type_id;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_photo() {
        return customer_photo;
    }

    public void setCustomer_photo(String customer_photo) {
        this.customer_photo = customer_photo;
    }

    public String getCustomer_location() {
        return customer_location;
    }

    public void setCustomer_location(String customer_location) {
        this.customer_location = customer_location;
    }

    public String getCustomer_latitude() {
        return customer_latitude;
    }

    public void setCustomer_latitude(String customer_latitude) {
        this.customer_latitude = customer_latitude;
    }

    public String getCustomer_longitude() {
        return customer_longitude;
    }

    public void setCustomer_longitude(String customer_longitude) {
        this.customer_longitude = customer_longitude;
    }

    public String getDestination_location() {
        return destination_location;
    }

    public void setDestination_location(String destination_location) {
        this.destination_location = destination_location;
    }

    public String getDestination_latitude() {
        return destination_latitude;
    }

    public void setDestination_latitude(String destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public String getDestination_longitude() {
        return destination_longitude;
    }

    public void setDestination_longitude(String destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public String getDri_phone_re_req() {
        return dri_phone_re_req;
    }

    public void setDri_phone_re_req(String dri_phone_re_req) {
        this.dri_phone_re_req = dri_phone_re_req;
    }
}
