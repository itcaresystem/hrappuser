package ride.happyy.user.listeners;

import ride.happyy.user.model.TripFeedbackBean;


public interface TripFeedbackListener {

    void onLoadFailed(String error);

    void onLoadCompleted(TripFeedbackBean tripFeedbackBean);
}
