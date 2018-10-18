package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.FareBean;
import ride.happyy.user.net.invokers.TotalFareInvoker;

public class TotalFareTask extends AsyncTask<String, Integer, FareBean> {

    private TotalFareTask.TotalFareTaskListener totalFareTaskListener;

    private HashMap<String, String> urlParams;
    private JSONObject postData;

    public TotalFareTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected FareBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        TotalFareInvoker totalFareInvoker = new TotalFareInvoker(null, postData);
        return totalFareInvoker.invokeTotalFareWS();

    }

    @Override
    protected void onPostExecute(FareBean result) {
        if (result != null)
            totalFareTaskListener.dataDownloadedSuccessfully(result);
        else
            totalFareTaskListener.dataDownloadFailed();
    }

    public interface TotalFareTaskListener {

        void dataDownloadedSuccessfully(FareBean fareBean);

        void dataDownloadFailed();

    }

    public TotalFareTaskListener getTotalFareTaskListener() {
        return totalFareTaskListener;
    }

    public void setTotalFareTaskListener(TotalFareTaskListener totalFareTaskListener) {
        this.totalFareTaskListener = totalFareTaskListener;
    }
}
