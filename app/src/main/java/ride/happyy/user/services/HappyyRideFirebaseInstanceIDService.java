package ride.happyy.user.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import ride.happyy.user.config.Config;
import ride.happyy.user.listeners.BasicListener;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.net.DataManager;

public class HappyyRideFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "HRFIService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance id token to your app server.

        if (Config.getInstance().getPhone() != null && !Config.getInstance().getPhone().equalsIgnoreCase("")) {
            JSONObject postData = getUpdateFCMTokenJSObj(refreshedToken);

            DataManager.performUpdateFCMToken(postData, new BasicListener() {
                @Override
                public void onLoadCompleted(BasicBean basicBean) {

                }

                @Override
                public void onLoadFailed(String error) {

                }
            });
        }


    }

    private JSONObject getUpdateFCMTokenJSObj(String fcmToken) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("fcm_token", fcmToken);
            postData.put("phone", Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

}
