package ride.happyy.user.listeners;


import ride.happyy.user.model.PromoCodeBean;

public interface PromoCodeListener {

    void onLoadCompleted(PromoCodeBean promoCodeBean);

    void onLoadFailed(String error);

}
