package ride.happyy.user.model;

import com.google.gson.annotations.SerializedName;

public class OnedayRequestResponse {
    @SerializedName("status")
    boolean statusString;
    @SerializedName("message")
    String messageString;

    public boolean isStatusString() {
        return statusString;
    }

    public String getMessageString() {
        return messageString;
    }
}
