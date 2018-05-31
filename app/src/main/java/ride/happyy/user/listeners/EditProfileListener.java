package ride.happyy.user.listeners;


import ride.happyy.user.model.UserBean;

public interface EditProfileListener {

    void onLoadCompleted(UserBean userBean);

    void onLoadFailed(String error);

}
