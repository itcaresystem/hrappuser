package ride.happyy.user.dialogs;


import android.app.Activity;
import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import ride.happyy.user.R;
import ride.happyy.user.app.App;
import ride.happyy.user.model.FeedbackBean;
import ride.happyy.user.model.SuccessBean;
import ride.happyy.user.util.AppConstants;

public class DriverGoodRatingDiialog {

    private final Activity mContext;
    private final SuccessBean successBean;
    private final FeedbackBean feedbackBean;
    private DialogGoodDriverRatingListener dialogGoodDriverRatingListener;
    private Dialog dialog;
    private TextView txtGoodRemarksDialogDate;
    private TextView txtGoodRemarksDialogTime;
    private TextView txtGoodRemarksDialogService;
    private TextView txtGoodRemarksDialogCleanliness;
    private TextView txtGoodRemarksDialogDriving;
    private TextView txtGoodRemarksDialogComfort;
    private TextView txtGoodRemarksDialogCarQuality;
    private TextView txtGoodRemarksDialogOther;
    private Button btnGoodRemarksDialogSubmit;
    private String GoodFeedbackType = AppConstants.GOOD_FEEDBACK_TYPE_SERVICE;
    private ArrayList<String> goodFeedbackList;
    private RatingBar ratingGoodRemark;

    public DriverGoodRatingDiialog(Activity mContext, SuccessBean successBean, FeedbackBean feedbackBean) {
        this.mContext = mContext;
        this.successBean = successBean;
        this.feedbackBean = feedbackBean;

        driverRatingGoodRemarksDialog();

        populateGoodRemarks();
    }

    public void show() {
        dialog.show();
    }

    private void driverRatingGoodRemarksDialog() {

        goodFeedbackList = new ArrayList<>();

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_driver_details_good_remarks);
        dialog.setCanceledOnTouchOutside(false);

        ratingGoodRemark = (RatingBar) dialog.findViewById(R.id.rb_good_remark);

        txtGoodRemarksDialogDate = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_date);
        txtGoodRemarksDialogTime = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_time);
        txtGoodRemarksDialogService = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_service);
        txtGoodRemarksDialogCleanliness = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_cleanliness);
        txtGoodRemarksDialogDriving = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_driving);
        txtGoodRemarksDialogComfort = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_comfort);
        txtGoodRemarksDialogCarQuality = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_car_quality);
        txtGoodRemarksDialogOther = (TextView) dialog.findViewById(R.id.txt_rating_page_good_remarks_other);

        btnGoodRemarksDialogSubmit = (Button) dialog.findViewById(R.id.btn_driver_good_remarks_submit);

        txtGoodRemarksDialogDate.setText(App.getUserDateFromUnix(String.valueOf(successBean.getTime())));
        txtGoodRemarksDialogTime.setText(App.getUserTimeFromUnix(String.valueOf(successBean.getTime())));

        ratingGoodRemark.setEnabled(false);

        txtGoodRemarksDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goodFeedbackList.contains(AppConstants.GOOD_FEEDBACK_TYPE_SERVICE)) {
                    goodFeedbackList.add(AppConstants.GOOD_FEEDBACK_TYPE_SERVICE);
                    txtGoodRemarksDialogService.setBackgroundResource(R.drawable.bg_round_edges);
                    txtGoodRemarksDialogService.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    goodFeedbackList.remove(AppConstants.GOOD_FEEDBACK_TYPE_SERVICE);
                    txtGoodRemarksDialogService.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtGoodRemarksDialogService.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));

                }
            }
        });

        txtGoodRemarksDialogCleanliness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goodFeedbackList.contains(AppConstants.GOOD_FEEDBACK_TYPE_CLEANLINESS)) {
                    goodFeedbackList.add(AppConstants.GOOD_FEEDBACK_TYPE_CLEANLINESS);
                    txtGoodRemarksDialogCleanliness.setBackgroundResource(R.drawable.bg_round_edges);
                    txtGoodRemarksDialogCleanliness.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    goodFeedbackList.remove(AppConstants.GOOD_FEEDBACK_TYPE_CLEANLINESS);
                    txtGoodRemarksDialogCleanliness.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtGoodRemarksDialogCleanliness.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtGoodRemarksDialogDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goodFeedbackList.contains(AppConstants.GOOD_FEEDBACK_TYPE_DRIVING)) {
                    goodFeedbackList.add(AppConstants.GOOD_FEEDBACK_TYPE_DRIVING);
                    txtGoodRemarksDialogDriving.setBackgroundResource(R.drawable.bg_round_edges);
                    txtGoodRemarksDialogDriving.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    goodFeedbackList.remove(AppConstants.GOOD_FEEDBACK_TYPE_DRIVING);
                    txtGoodRemarksDialogDriving.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtGoodRemarksDialogDriving.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtGoodRemarksDialogComfort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goodFeedbackList.contains(AppConstants.GOOD_FEEDBACK_TYPE_COMFORT)) {
                    goodFeedbackList.add(AppConstants.GOOD_FEEDBACK_TYPE_COMFORT);
                    txtGoodRemarksDialogComfort.setBackgroundResource(R.drawable.bg_round_edges);
                    txtGoodRemarksDialogComfort.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    goodFeedbackList.remove(AppConstants.GOOD_FEEDBACK_TYPE_COMFORT);
                    txtGoodRemarksDialogComfort.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtGoodRemarksDialogComfort.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtGoodRemarksDialogCarQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goodFeedbackList.contains(AppConstants.GOOD_FEEDBACK_TYPE_CAR_QUALITY)) {
                    goodFeedbackList.add(AppConstants.GOOD_FEEDBACK_TYPE_CAR_QUALITY);
                    txtGoodRemarksDialogCarQuality.setBackgroundResource(R.drawable.bg_round_edges);
                    txtGoodRemarksDialogCarQuality.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    goodFeedbackList.remove(AppConstants.GOOD_FEEDBACK_TYPE_CAR_QUALITY);
                    txtGoodRemarksDialogCarQuality.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtGoodRemarksDialogCarQuality.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtGoodRemarksDialogOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!goodFeedbackList.contains(AppConstants.GOOD_FEEDBACK_TYPE_OTHER)) {
                    goodFeedbackList.add(AppConstants.GOOD_FEEDBACK_TYPE_OTHER);
                    txtGoodRemarksDialogOther.setBackgroundResource(R.drawable.bg_round_edges);
                    txtGoodRemarksDialogOther.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    goodFeedbackList.remove(AppConstants.GOOD_FEEDBACK_TYPE_OTHER);
                    txtGoodRemarksDialogOther.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtGoodRemarksDialogOther.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        btnGoodRemarksDialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedbackBean.setGoodFeedBackList((goodFeedbackList));
                dialogGoodDriverRatingListener.onDriverGoodFeedbackSubmit(feedbackBean);
                dialog.dismiss();
            }
        });

        show();
    }

    private void populateGoodRemarks() {

        ratingGoodRemark.setRating(feedbackBean.getRating());
    }

    public interface DialogGoodDriverRatingListener {

        void onDriverGoodFeedbackSubmit(FeedbackBean feedbackBean);

    }

    public DialogGoodDriverRatingListener getDialogGoodDriverRatingListener() {
        return dialogGoodDriverRatingListener;
    }

    public void setDialogGoodDriverRatingListener(DialogGoodDriverRatingListener dialogGoodDriverRatingListener) {
        this.dialogGoodDriverRatingListener = dialogGoodDriverRatingListener;

    }
}
