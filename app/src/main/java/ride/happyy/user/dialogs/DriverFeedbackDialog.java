package ride.happyy.user.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ride.happyy.user.R;
import ride.happyy.user.app.App;
import ride.happyy.user.model.FeedbackBean;
import ride.happyy.user.model.SuccessBean;

public class DriverFeedbackDialog {

    private final Activity mContext;
    private final SuccessBean successBean;
    private final FeedbackBean feedbackBean;
    private DriverFeedbackDialogListener driverFeedbackDialogListener;
    private Dialog dialog;
    private EditText etxtDriverFeedback;
    private Button btnFeedBackDone;
    private String str;
    private TextView txtFeedbackPageDate;
    private TextView txtFeedbackPageTime;

    public DriverFeedbackDialog(Activity mContext, SuccessBean successBean, FeedbackBean feedbackBean) {
        this.mContext = mContext;
        this.successBean = successBean;
        this.feedbackBean = feedbackBean;

        driverRatingFeedBackDialog();
    }

    public void show() {
        dialog.show();
    }

    private void driverRatingFeedBackDialog() {

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_driver_rating_feedback);
        dialog.setCanceledOnTouchOutside(false);

        txtFeedbackPageDate = (TextView) dialog.findViewById(R.id.txt_feedback_page_date);
        txtFeedbackPageTime = (TextView) dialog.findViewById(R.id.txt_feedback_page_time);

        etxtDriverFeedback = (EditText) dialog.findViewById(R.id.etxt_driver_feedback);
        btnFeedBackDone = (Button) dialog.findViewById(R.id.btn_driver_feed_completed);

        txtFeedbackPageDate.setText(App.getUserDateFromUnix(String.valueOf(successBean.getTime())));
        txtFeedbackPageTime.setText(App.getUserTimeFromUnix(String.valueOf(successBean.getTime())));


        btnFeedBackDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = etxtDriverFeedback.getText().toString();

                feedbackBean.setFeedBack(str);
                driverFeedbackDialogListener.onDriverFeedbackSubmit(feedbackBean);
                dialog.dismiss();
            }
        });

        show();
    }

    public interface DriverFeedbackDialogListener {

        void onDriverFeedbackSubmit(FeedbackBean feedbackBean);

    }

    public DriverFeedbackDialogListener getDriverFeedbackDialogListener() {
        return driverFeedbackDialogListener;
    }

    public void setDriverFeedbackDialogListener(DriverFeedbackDialogListener driverFeedbackDialogListener) {
        this.driverFeedbackDialogListener = driverFeedbackDialogListener;
    }
}
