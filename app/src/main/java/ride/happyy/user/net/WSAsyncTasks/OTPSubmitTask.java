package ride.happyy.user.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import ride.happyy.user.model.OTPBean;
import ride.happyy.user.net.invokers.OTPSubmitInvoker;


public class OTPSubmitTask extends AsyncTask<String, Integer, OTPBean> {

    private OTPSubmitTask.OTPSubmitTaskListener otpSubmitTaskListener;

    private JSONObject postData;

    public OTPSubmitTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected OTPBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        OTPSubmitInvoker otpSubmitInvoker = new OTPSubmitInvoker(null, postData);
        return otpSubmitInvoker.invokeOTPSubmitWS();
    }

    @Override
    protected void onPostExecute(OTPBean result) {
        super.onPostExecute(result);
        if (result != null)
            otpSubmitTaskListener.dataDownloadedSuccessfully(result);
        else
            otpSubmitTaskListener.dataDownloadFailed();
    }

    public static interface OTPSubmitTaskListener {

        void dataDownloadedSuccessfully(OTPBean otpBean);

        void dataDownloadFailed();
    }

    public OTPSubmitTaskListener getOtpSubmitTaskListener() {
        return otpSubmitTaskListener;
    }

    public void setOtpSubmitTaskListener(OTPSubmitTaskListener otpSubmitTaskListener) {
        this.otpSubmitTaskListener = otpSubmitTaskListener;
    }
}