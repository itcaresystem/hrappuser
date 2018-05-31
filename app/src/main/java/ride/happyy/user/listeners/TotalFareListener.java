package ride.happyy.user.listeners;


import ride.happyy.user.model.FareBean;

public interface TotalFareListener {

    void onLoadCompleted(FareBean fareBean);

    void onLoadFailed(String error);
}
