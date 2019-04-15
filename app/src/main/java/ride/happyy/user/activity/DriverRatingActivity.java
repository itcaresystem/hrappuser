package ride.happyy.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.R;
import ride.happyy.user.config.Config;
import ride.happyy.user.dialogs.DriverBadRatingDialog;
import ride.happyy.user.dialogs.DriverFeedbackDialog;
import ride.happyy.user.dialogs.DriverGoodRatingDiialog;
import ride.happyy.user.dialogs.DriverRatingDialog;
import ride.happyy.user.listeners.DriverRatingListener;
import ride.happyy.user.listeners.SuccessListener;
import ride.happyy.user.model.DriverBean;
import ride.happyy.user.model.DriverRatingBean;
import ride.happyy.user.model.FeedbackBean;
import ride.happyy.user.model.SuccessBean;
import ride.happyy.user.net.DataManager;

public class DriverRatingActivity extends BaseAppCompatNoDrawerActivity {

    private static final String TAG = "";
    private SuccessBean successBean;
    private FeedbackBean feedbackBean;
    private String tripID;
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private  String mRatingValue="";
    private ImageView driverPhoto;
    private TextView driverName,feedBackTotalFareTV;
    private DriverBean driverBean;
    private ProgressBar simpleProgressBarRating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_rating);
        getSupportActionBar().hide();

        tripID = getIntent().getStringExtra("trip_id");
        driverBean =(DriverBean) getIntent().getSerializableExtra("driverdetails");
        Log.i(TAG, "onCreate: TripId" + tripID);
       // fetchTripCompletionDetails();
        initViews();
        addListenerOnRatingBar();
        addListenerOnButton();
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                txtRatingValue.setText(String.valueOf(rating));
                mRatingValue=String.valueOf(rating);


            }
        });
    }
    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(DriverRatingActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();
                performDriverRating();

            }

        });

    }
/*

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, SplashActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }
    */


    public void initViews() {
        driverPhoto=findViewById(R.id.driverPhotoIMV);
        driverName=findViewById(R.id.driverNameTV);
        feedBackTotalFareTV=findViewById(R.id.feedBackTotalFareTV);
        simpleProgressBarRating=findViewById(R.id.simpleProgressBarRating);
        feedbackBean = new FeedbackBean();

        driverName.setText(driverBean.getDriverName());
        feedBackTotalFareTV.setText("৳ "+driverBean.getFare());

        Glide.with(getApplicationContext())
                .load(driverBean.getDriverPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.cng)
                        .fallback(R.drawable.cng)
                        .centerCrop()
                        .circleCrop())
                .into(driverPhoto);


    }

    public void performDriverRating() {
        simpleProgressBarRating.setVisibility(View.VISIBLE);

        JSONObject postData = getFeedbackJSObj();

        DataManager.performDriverRating(postData, new DriverRatingListener() {

            @Override
            public void onLoadCompleted(DriverRatingBean driverRatingBean) {
                simpleProgressBarRating.setVisibility(View.GONE);
                swipeView.setRefreshing(false);
                startActivity(new Intent(DriverRatingActivity.this, SplashActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);

            }
        });
    }

    private JSONObject getFeedbackJSObj() {
        JSONObject postData = new JSONObject();
        JSONArray badFeedback = new JSONArray();
        JSONArray goodFeedback = new JSONArray();

        try {
            postData.put("trip_id", tripID);
            postData.put("rating",mRatingValue);
            postData.put("phone", Config.getInstance().getPhone());
            /*
            postData.put("rating", feedbackBean.getRating());

            for (String str1 : feedbackBean.getBadFeedBackList()) {
                badFeedback.put(str1);
            }
            postData.put("bad_feedback", badFeedback);

            for (String str2 : feedbackBean.getGoodFeedBackList()) {
                goodFeedback.put(str2);
            }
            postData.put("good_feedback", goodFeedback);
            postData.put("driver_feedback", feedbackBean.getFeedBack());

            Log.i(TAG, "getFeedbackJSObj: Rating" + feedbackBean.getRating());
            */

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
/*
    public void fetchTripCompletionDetails() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("id", tripID);

        DataManager.fetchTripCompletionDetails(urlParams, new SuccessListener() {

            @Override
            public void onLoadCompleted(SuccessBean successBeanWS) {
//                populateUserInfo(successBean);

                successBean = successBeanWS;

                if (!isFinishing())
                    driverRatingDialog();
            }

            @Override
            public void onLoadFailed(String errorMsg) {


            }
        });
    }


    public void driverRatingDialog() {

        DriverRatingDialog driverRatingDialog = new DriverRatingDialog(this, successBean, feedbackBean);
        driverRatingDialog.setDialogDriverRatingListener(new DriverRatingDialog.DialogDriverRatingListener() {

            private FeedbackBean feedbackBean;

            @Override
            public void onDriverRatingSubmit(FeedbackBean feedbackBean) {

                this.feedbackBean = feedbackBean;
                driverRatingBadRemarksDialog(feedbackBean, successBean);
            }
        });
        driverRatingDialog.show();
    }

    public void driverRatingBadRemarksDialog(final FeedbackBean feedbackBean, final SuccessBean successBean) {

        DriverBadRatingDialog driverBadRatingDialog = new DriverBadRatingDialog(this, this.successBean, this.feedbackBean);
        driverBadRatingDialog.setDialogBadDriverRatingListener(new DriverBadRatingDialog.DialogBadDriverRatingListener() {

            private FeedbackBean feedbackBean;

            @Override
            public void onDriverBadRatingSubmit(FeedbackBean feedbackBean) {

                this.feedbackBean = feedbackBean;
                driverRatingGoodRemarksDialog(feedbackBean, successBean);
            }
        });
        driverBadRatingDialog.show();

    }

    public void driverRatingGoodRemarksDialog(final FeedbackBean feedbackBean, final SuccessBean successBean) {

        DriverGoodRatingDiialog driverGoodRatingDiialog = new DriverGoodRatingDiialog(this, this.successBean, this.feedbackBean);
        driverGoodRatingDiialog.setDialogGoodDriverRatingListener(new DriverGoodRatingDiialog.DialogGoodDriverRatingListener() {

            private FeedbackBean feedbackBean;

            @Override
            public void onDriverGoodFeedbackSubmit(FeedbackBean feedbackBean) {

                this.feedbackBean = feedbackBean;
                driverRatingFeedBackDialog(feedbackBean, successBean);
            }
        });
        driverGoodRatingDiialog.show();
    }

    public void driverRatingFeedBackDialog(FeedbackBean feedbackBean, SuccessBean successBean) {

        DriverFeedbackDialog driverFeedbackDialog = new DriverFeedbackDialog(this, this.successBean, this.feedbackBean);
        driverFeedbackDialog.setDriverFeedbackDialogListener(new DriverFeedbackDialog.DriverFeedbackDialogListener() {

            private FeedbackBean feedbackBean;

            @Override
            public void onDriverFeedbackSubmit(FeedbackBean feedbackBean) {

                this.feedbackBean = feedbackBean;
                performDriverRating();
                finish();
            }
        });
        driverFeedbackDialog.show();
    }
*/

}

