package ride.happyy.user.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import ride.happyy.user.R;
import ride.happyy.user.app.App;
import ride.happyy.user.dialogs.PopupMessage;
import ride.happyy.user.listeners.AppStatusListener;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.model.DriverBean;
import ride.happyy.user.net.DataManager;
import ride.happyy.user.net.parsers.TripEndParser;
import ride.happyy.user.util.AppConstants;

public class SplashActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "Current Time";
    private String tripID = "";
    private DriverBean driverBean;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private PopupMessage popupMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Log.i(TAG, "onCreate: Key : " + key + " Value: " + getIntent().getExtras().get(key));
            }
        }
        if (getIntent().hasExtra("response")) {
            String body = getIntent().getStringExtra("response");
            TripEndParser tripEndParser = new TripEndParser();
            BasicBean basicBean = tripEndParser.parseBasicResponse(body);

            if (basicBean != null) {
                if (basicBean.getStatus().equalsIgnoreCase("Success")) {
//                    initiateDriverRatingService(basicBean.getId());
                    tripID = basicBean.getId();
                }
            }
        }

        initViews();

        new Handler().postDelayed(checkDriverTask, 500);
//        new Handler().postDelayed(splashTask, 2000);
    }

    private void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(false, false);
                getData(true);
            }
        };

    }


    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchAppStatus();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    Runnable splashTask = new Runnable() {
        @Override
        public void run() {
            navigate();
        }
    };


    Runnable checkDriverTask = new Runnable() {
        @Override
        public void run() {

            if (App.checkForToken() && fop.checkSPHash()
//                    && Config.getInstance().isPhoneVerified()
                    && tripID.equalsIgnoreCase("")) {
                getData(false);
            } else {
                new Handler().postDelayed(splashTask, 2000);
            }
        }
    };

    private void navigate() {

        Log.i(TAG, "navigate: Trip id : " + tripID);

        if (App.checkForToken() && fop.checkSPHash()) {
//            if (Config.getInstance().isPhoneVerified()) {
            if (tripID.equalsIgnoreCase("")) {

                if (driverBean != null && driverBean.getAppStatus() == 0) {
                    startActivity(new Intent(SplashActivity.this, LandingPageActivity.class));
                } else {
                    Log.i(TAG, "navigate: TripBean : " + new Gson().toJson(driverBean));
                    startActivity(new Intent(SplashActivity.this, OnTripActivity.class)
                            .putExtra("bean", driverBean));
                }

//                    startActivity(new Intent(SplashActivity.this, LandingPageActivity.class));
            } else
                startActivity(new Intent(SplashActivity.this, DriverRatingActivity.class)
                        .putExtra("id", tripID));
            //                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
            finish();
            /*} else {
                startActivity(new Intent(SplashActivity.this, LandingPageActivity.class));
                finish();
            }*/
        } else {
            App.logout();
            startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
            finish();
        }

    }

    private void fetchAppStatus() {

        HashMap<String, String> urlParams = new HashMap<>();

        DataManager.fetchAppStatus(urlParams, new AppStatusListener() {
            @Override
            public void onLoadCompleted(DriverBean driverBeanWS) {
                Log.i(TAG, "onLoadCompleted: APP STATUS DRIVER BEAN : " + new Gson().toJson(driverBeanWS));

                driverBean = driverBeanWS;
                new Handler().postDelayed(splashTask, 2000);
            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!isDestroyed() && !isFinishing()) {
                        showErrorPopup(error);
                    }
                } else {
                    if (!isFinishing()) {
                        showErrorPopup(error);
                    }
                }
            }
        });

    }

    private void showErrorPopup(String error) {
        if (popupMessage == null)
            popupMessage = new PopupMessage(SplashActivity.this);
        popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
            @Override
            public void actionCompletedSuccessfully(boolean result) {
                getData(false);
            }

            @Override
            public void actionFailed() {
                Toast.makeText(SplashActivity.this, R.string.message_thank_you, Toast.LENGTH_SHORT).show();
                App.logout();
                finish();
            }
        });
        popupMessage.show(error, 0, getString(R.string.btn_retry), getString(R.string.btn_cancel));
    }

}


