package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.DriverBean;
import ride.happyy.user.net.invokers.AppStatusInvoker;

public class AppStatusTask extends AsyncTask<String, Integer, DriverBean> {

    private AppStatusTaskListener appStatusTaskListener;

    private HashMap<String, String> urlParams;
    private JSONObject postData;

    public AppStatusTask(JSONObject postData) {
        super();
       // this.urlParams = urlParams;
        this.postData = postData;
    }

    @Override
    protected DriverBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        AppStatusInvoker appStatusInvoker = new AppStatusInvoker(null, postData);
        return appStatusInvoker.invokeAppStatusWS();
    }

    @Override
    protected void onPostExecute(DriverBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            appStatusTaskListener.dataDownloadedSuccessfully(result);
        else
            appStatusTaskListener.dataDownloadFailed();
    }

    public static interface AppStatusTaskListener {

        void dataDownloadedSuccessfully(DriverBean driverBean);

        void dataDownloadFailed();
    }

    public AppStatusTaskListener getAppStatusTaskListener() {
        return appStatusTaskListener;
    }

    public void setAppStatusTaskListener(AppStatusTaskListener appStatusTaskListener) {
        this.appStatusTaskListener = appStatusTaskListener;
    }
}
