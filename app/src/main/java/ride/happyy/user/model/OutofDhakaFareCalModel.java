package ride.happyy.user.model;

import com.google.gson.annotations.SerializedName;

public class OutofDhakaFareCalModel {
    @SerializedName("phone")
    String phone ;
    @SerializedName("district_from")
    String district_from ;
    @SerializedName("district_to")
    String district_to ;

    public OutofDhakaFareCalModel(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrict_from() {
        return district_from;
    }

    public void setDistrict_from(String district_from) {
        this.district_from = district_from;
    }

    public String getDistrict_to() {
        return district_to;
    }

    public void setDistrict_to(String district_to) {
        this.district_to = district_to;
    }
}
