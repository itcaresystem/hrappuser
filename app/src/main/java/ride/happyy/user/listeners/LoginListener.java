package ride.happyy.user.listeners;


import ride.happyy.user.model.AuthBean;

public interface LoginListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
