package ride.happyy.user.listeners;


import ride.happyy.user.model.CarBean;

public interface CarInfoListener {

    void onLoadFailed(String error);

    void onLoadCompleted(CarBean carBean);

}
