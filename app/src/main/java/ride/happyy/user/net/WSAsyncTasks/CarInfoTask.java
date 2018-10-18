package ride.happyy.user.net.WSAsyncTasks;


import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.CarBean;
import ride.happyy.user.net.invokers.CarInfoInvoker;

public class CarInfoTask extends AsyncTask<String, Integer, CarBean> {

    private CarInfoTask.CarInfoTaskListener carInfoTaskListener;

    private HashMap<String, String> urlParams;
    JSONObject postData;

    public CarInfoTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    /*public CarInfoTask(JSONObject urlParams) {

    }*/

    @Override
    protected CarBean doInBackground(String... params) {

        System.out.println(">>>>>>>>>doInBackground");
        CarInfoInvoker carInfoInvoker = new CarInfoInvoker(null, postData);
        return carInfoInvoker.invokeCarInfoWS();
    }

    @Override
    protected void onPostExecute(CarBean result) {
        if (result != null)
            carInfoTaskListener.dataDownloadedSuccessfully(result);
        else
            carInfoTaskListener.dataDownloadFailed();
    }

    public interface CarInfoTaskListener {
        void dataDownloadedSuccessfully(CarBean carBean);

        void dataDownloadFailed();
    }

    public CarInfoTaskListener getCarInfoTaskListener() {
        return carInfoTaskListener;
    }

    public void setCarInfoTaskListener(CarInfoTaskListener carInfoTaskListener) {
        this.carInfoTaskListener = carInfoTaskListener;
    }
}
