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

public class DriverBadRatingDialog {

    private final Activity mContext;
    private final SuccessBean successBean;
    private final FeedbackBean feedbackBean;
    private DialogBadDriverRatingListener dialogBadDriverRatingListener;
    private Dialog dialog;
    private TextView txtBadRemarksDialogDate;
    private TextView txtBadRemarksDialogTime;
    private TextView txtBadRemarksDialogService;
    private TextView txtBadRemarksDialogCleanliness;
    private TextView txtBadRemarksDialogDriving;
    private TextView txtBadRemarksDialogComfort;
    private TextView txtBadRemarksDialogCarQuality;
    private TextView txtBadRemarksDialogOther;
    private Button btnBadRemarksDialogSubmit;
    private String badFeedbackType = AppConstants.BAD_FEEDBACK_TYPE_SERVICE;
    private ArrayList<String> badFeedbackList;
    private RatingBar ratingBadRemark;

    public DriverBadRatingDialog(Activity mContext, SuccessBean successBean, FeedbackBean feedbackBean) {
        this.mContext = mContext;
        this.successBean = successBean;
        this.feedbackBean = feedbackBean;

        driverRatingBadRemarksDialog();

        populateBadRemarks();
    }

    public void show() {
        dialog.show();
    }

    private void driverRatingBadRemarksDialog() {

        badFeedbackList = new ArrayList<>();

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_driver_rating_bad_remarks);
        dialog.setCanceledOnTouchOutside(false);

        ratingBadRemark = (RatingBar) dialog.findViewById(R.id.rb_bad_remark);

        txtBadRemarksDialogDate = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_date);
        txtBadRemarksDialogTime = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_time);
        txtBadRemarksDialogService = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_service);
        txtBadRemarksDialogCleanliness = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_cleanliness);
        txtBadRemarksDialogDriving = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_driving);
        txtBadRemarksDialogComfort = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_comfort);
        txtBadRemarksDialogCarQuality = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_car_quality);
        txtBadRemarksDialogOther = (TextView) dialog.findViewById(R.id.txt_rating_page_bad_remarks_other);

        btnBadRemarksDialogSubmit = (Button) dialog.findViewById(R.id.btn_driver_bad_remarks_submit);
        txtBadRemarksDialogDate.setText(App.getUserDateFromUnix(String.valueOf(successBean.getTime())));
        txtBadRemarksDialogTime.setText(App.getUserTimeFromUnix(String.valueOf(successBean.getTime())));

        ratingBadRemark.setEnabled(false);

        ratingBadRemark.setRating(feedbackBean.getRating());
        txtBadRemarksDialogService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!badFeedbackList.contains(AppConstants.BAD_FEEDBACK_TYPE_SERVICE)) {
                    badFeedbackList.add(AppConstants.BAD_FEEDBACK_TYPE_SERVICE);
                    txtBadRemarksDialogService.setBackgroundResource(R.drawable.bg_round_edges);
                    txtBadRemarksDialogService.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));

                } else {

                    badFeedbackList.remove(AppConstants.BAD_FEEDBACK_TYPE_SERVICE);
                    txtBadRemarksDialogService.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtBadRemarksDialogService.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtBadRemarksDialogCleanliness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!badFeedbackList.contains(AppConstants.BAD_FEEDBACK_TYPE_CLEANLINESS)) {
                    badFeedbackList.add(AppConstants.BAD_FEEDBACK_TYPE_CLEANLINESS);
                    txtBadRemarksDialogCleanliness.setBackgroundResource(R.drawable.bg_round_edges);
                    txtBadRemarksDialogCleanliness.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));
                } else {

                    badFeedbackList.remove(AppConstants.BAD_FEEDBACK_TYPE_CLEANLINESS);
                    txtBadRemarksDialogCleanliness.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtBadRemarksDialogCleanliness.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtBadRemarksDialogDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!badFeedbackList.contains(AppConstants.BAD_FEEDBACK_TYPE_DRIVING)) {
                    badFeedbackList.add(AppConstants.BAD_FEEDBACK_TYPE_DRIVING);
                    txtBadRemarksDialogDriving.setBackgroundResource(R.drawable.bg_round_edges);
                    txtBadRemarksDialogDriving.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));
                } else {

                    badFeedbackList.remove(AppConstants.BAD_FEEDBACK_TYPE_DRIVING);
                    txtBadRemarksDialogDriving.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtBadRemarksDialogDriving.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtBadRemarksDialogComfort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!badFeedbackList.contains(AppConstants.BAD_FEEDBACK_TYPE_COMFORT)) {
                    badFeedbackList.add(AppConstants.BAD_FEEDBACK_TYPE_COMFORT);
                    txtBadRemarksDialogComfort.setBackgroundResource(R.drawable.bg_round_edges);
                    txtBadRemarksDialogComfort.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));
                } else {

                    badFeedbackList.remove(AppConstants.BAD_FEEDBACK_TYPE_COMFORT);
                    txtBadRemarksDialogComfort.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtBadRemarksDialogComfort.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtBadRemarksDialogCarQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!badFeedbackList.contains(AppConstants.BAD_FEEDBACK_TYPE_CAR_QUALITY)) {
                    badFeedbackList.add(AppConstants.BAD_FEEDBACK_TYPE_CAR_QUALITY);
                    txtBadRemarksDialogCarQuality.setBackgroundResource(R.drawable.bg_round_edges);
                    txtBadRemarksDialogCarQuality.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));
                } else {

                    badFeedbackList.remove(AppConstants.BAD_FEEDBACK_TYPE_CAR_QUALITY);
                    txtBadRemarksDialogCarQuality.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtBadRemarksDialogCarQuality.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        txtBadRemarksDialogOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!badFeedbackList.contains(AppConstants.BAD_FEEDBACK_TYPE_OTHER)) {
                    badFeedbackList.add(AppConstants.BAD_FEEDBACK_TYPE_OTHER);
                    txtBadRemarksDialogOther.setBackgroundResource(R.drawable.bg_round_edges);
                    txtBadRemarksDialogOther.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.white)));
                } else {

                    badFeedbackList.remove(AppConstants.BAD_FEEDBACK_TYPE_OTHER);
                    txtBadRemarksDialogOther.setBackgroundResource(R.drawable.bg_round_edges_gray);
                    txtBadRemarksDialogOther.setTextColor((ContextCompat.getColor(mContext.getApplicationContext(), R.color.text_feedback)));
                }
            }
        });

        btnBadRemarksDialogSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                feedbackBean.setBadFeedBackList(badFeedbackList);
                dialogBadDriverRatingListener.onDriverBadRatingSubmit(feedbackBean);
                dialog.dismiss();
            }
        });

        show();
    }

    private void populateBadRemarks() {

        ratingBadRemark.setRating(feedbackBean.getRating());
    }

    public interface DialogBadDriverRatingListener {

        void onDriverBadRatingSubmit(FeedbackBean feedbackBean);
    }

    public DialogBadDriverRatingListener getDialogBadDriverRatingListener() {
        return dialogBadDriverRatingListener;
    }

    public void setDialogBadDriverRatingListener(DialogBadDriverRatingListener dialogBadDriverRatingListener) {
        this.dialogBadDriverRatingListener = dialogBadDriverRatingListener;

    }
}
