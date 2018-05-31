package ride.happyy.user.listeners;

import ride.happyy.user.model.OTPBean;


public interface OTPSubmitListener {

    void onLoadCompleted(OTPBean otpBean);

    void onLoadFailed(String error);

}
