package ride.happyy.user.listeners;

import ride.happyy.user.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);

}
