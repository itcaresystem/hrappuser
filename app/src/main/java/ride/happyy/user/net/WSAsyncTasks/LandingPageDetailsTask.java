package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.LandingPageBean;
import ride.happyy.user.net.invokers.LandingPageDetailsInvoker;

public class LandingPageDetailsTask extends AsyncTask<String, Integer, LandingPageBean> {

    private LandingPageDetailsTaskListener landingPageDetailsTaskListener;

    private HashMap<String, String> urlParams;
    private JSONObject postData;

    public LandingPageDetailsTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected LandingPageBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        LandingPageDetailsInvoker landingPageDetailsInvoker = new LandingPageDetailsInvoker(null, postData);
        return landingPageDetailsInvoker.invokeLandingPageDetailsWS();
    }

    @Override
    protected void onPostExecute(LandingPageBean result) {
        if (result != null)
            landingPageDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            landingPageDetailsTaskListener.dataDownloadFailed();
    }

    public interface LandingPageDetailsTaskListener {

        void dataDownloadedSuccessfully(LandingPageBean landingPageBean);

        void dataDownloadFailed();
    }

    public LandingPageDetailsTaskListener getLandingPageDetailsTaskListener() {
        return landingPageDetailsTaskListener;
    }

    public void setLandingPageDetailsTaskListener(LandingPageDetailsTaskListener landingPageDetailsTaskListener) {
        this.landingPageDetailsTaskListener = landingPageDetailsTaskListener;
    }
}
