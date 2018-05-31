package ride.happyy.user.listeners;


import ride.happyy.user.model.LandingPageBean;

public interface LandingPageDetailsListener {

    void onLoadCompleted(LandingPageBean landingPageListBean);

    void onLoadFailed(String error);
}
