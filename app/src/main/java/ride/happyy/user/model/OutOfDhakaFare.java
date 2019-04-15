package ride.happyy.user.model;

import com.google.gson.annotations.SerializedName;

public class OutOfDhakaFare {
    @SerializedName("premio_rent")
    private String primioRent;
    @SerializedName("noah_rent")
    private String noahRent;
    @SerializedName("hiace_rent")
    private String hiaceRent;
    @SerializedName("drop_address")
    private String dropOffAddress;


    public String getPrimioRent() {
        return primioRent;
    }

    public void setPrimioRent(String primioRent) {
        this.primioRent = primioRent;
    }

    public String getNoahRent() {
        return noahRent;
    }

    public void setNoahRent(String noahRent) {
        this.noahRent = noahRent;
    }

    public String getHiaceRent() {
        return hiaceRent;
    }

    public void setHiaceRent(String hiaceRent) {
        this.hiaceRent = hiaceRent;
    }

    public String getDropOffAddress() {
        return dropOffAddress;
    }

    public void setDropOffAddress(String dropOffAddress) {
        this.dropOffAddress = dropOffAddress;
    }
}
