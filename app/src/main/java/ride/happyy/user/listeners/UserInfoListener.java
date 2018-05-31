package ride.happyy.user.listeners;


import ride.happyy.user.model.UserBean;

public interface UserInfoListener {

    void onLoadCompleted(UserBean userBean);

    void onLoadFailed(String error);

}
