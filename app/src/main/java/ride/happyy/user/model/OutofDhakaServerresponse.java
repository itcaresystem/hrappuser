package ride.happyy.user.model;

import com.google.gson.annotations.SerializedName;

public class OutofDhakaServerresponse {
    @SerializedName("status")
    boolean statusString;
    @SerializedName("outofdhaka_request_id")
    String outofDhakaRequestId;

    public boolean isSuccess(){
        return statusString;
    }

    public String getOutofDhakaRequestId() {
        return outofDhakaRequestId;
    }
}
