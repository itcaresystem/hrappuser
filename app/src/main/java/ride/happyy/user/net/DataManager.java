package ride.happyy.user.net;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ride.happyy.user.listeners.AppStatusListener;
import ride.happyy.user.listeners.BasicListener;
import ride.happyy.user.listeners.CarInfoListener;
import ride.happyy.user.listeners.DriverDetailsListener;
import ride.happyy.user.listeners.DriverRatingListener;
import ride.happyy.user.listeners.EditProfileListener;
import ride.happyy.user.listeners.FreeRideListener;
import ride.happyy.user.listeners.LandingPageListener;
import ride.happyy.user.listeners.LocationSaveListener;
import ride.happyy.user.listeners.LoginListener;
import ride.happyy.user.listeners.OTPResendCodeListener;
import ride.happyy.user.listeners.PolyPointsListener;
import ride.happyy.user.listeners.PromoCodeListener;
import ride.happyy.user.listeners.RegistrationListener;
import ride.happyy.user.listeners.RequestRideListener;
import ride.happyy.user.listeners.RequestStatusListener;
import ride.happyy.user.listeners.SavedLocationListener;
import ride.happyy.user.listeners.SuccessListener;
import ride.happyy.user.listeners.TotalFareListener;
import ride.happyy.user.listeners.TripCancellationListener;
import ride.happyy.user.listeners.TripDetailsListener;
import ride.happyy.user.listeners.TripFeedbackListener;
import ride.happyy.user.listeners.TripListListener;
import ride.happyy.user.listeners.UserInfoListener;
import ride.happyy.user.model.AuthBean;
import ride.happyy.user.model.BasicBean;
import ride.happyy.user.model.CarBean;
import ride.happyy.user.model.DriverBean;
import ride.happyy.user.model.DriverRatingBean;
import ride.happyy.user.model.FareBean;
import ride.happyy.user.model.FreeRideBean;
import ride.happyy.user.model.LandingPageBean;
import ride.happyy.user.model.LocationBean;
import ride.happyy.user.model.PolyPointsBean;
import ride.happyy.user.model.PromoCodeBean;
import ride.happyy.user.model.RequestBean;
import ride.happyy.user.model.SuccessBean;
import ride.happyy.user.model.TripCancellationBean;
import ride.happyy.user.model.TripDetailsBean;
import ride.happyy.user.model.TripFeedbackBean;
import ride.happyy.user.model.TripListBean;
import ride.happyy.user.model.UserBean;
import ride.happyy.user.net.WSAsyncTasks.AppStatusTask;
import ride.happyy.user.net.WSAsyncTasks.CarInfoTask;
import ride.happyy.user.net.WSAsyncTasks.DriverDetailsTask;
import ride.happyy.user.net.WSAsyncTasks.DriverRatingTask;
import ride.happyy.user.net.WSAsyncTasks.EditProfileTask;
import ride.happyy.user.net.WSAsyncTasks.FreeRideTask;
import ride.happyy.user.net.WSAsyncTasks.LandingPageDetailsTask;
import ride.happyy.user.net.WSAsyncTasks.LocationSaveTask;
import ride.happyy.user.net.WSAsyncTasks.LoginTask;
import ride.happyy.user.net.WSAsyncTasks.NewPasswordTask;
import ride.happyy.user.net.WSAsyncTasks.OTPResendCodeTask;
import ride.happyy.user.net.WSAsyncTasks.MobileAvailabilityCheckTask;
import ride.happyy.user.net.WSAsyncTasks.PolyPointsTask;
import ride.happyy.user.net.WSAsyncTasks.PromoCodeTask;
import ride.happyy.user.net.WSAsyncTasks.RegistrationTask;
import ride.happyy.user.net.WSAsyncTasks.RequestCancelTask;
import ride.happyy.user.net.WSAsyncTasks.RequestRideTask;
import ride.happyy.user.net.WSAsyncTasks.RequestStatusTask;
import ride.happyy.user.net.WSAsyncTasks.RequestTriggeringTask;
import ride.happyy.user.net.WSAsyncTasks.SavedLocationTask;
import ride.happyy.user.net.WSAsyncTasks.SuccessDetailsTask;
import ride.happyy.user.net.WSAsyncTasks.TotalFareTask;
import ride.happyy.user.net.WSAsyncTasks.TripCancellationTask;
import ride.happyy.user.net.WSAsyncTasks.TripDetailsTask;
import ride.happyy.user.net.WSAsyncTasks.TripFeedbackTask;
import ride.happyy.user.net.WSAsyncTasks.TripListTask;
import ride.happyy.user.net.WSAsyncTasks.UpdateFCMTokenTask;
import ride.happyy.user.net.WSAsyncTasks.UserInfoTask;
import ride.happyy.user.util.AppConstants;

public class DataManager {

    public static void performUpdateFCMToken(JSONObject postData, final BasicListener listener) {

        UpdateFCMTokenTask updateFCMTokenTask = new UpdateFCMTokenTask(postData);
        updateFCMTokenTask.setUpdateFCMTokenTaskListener(new UpdateFCMTokenTask.UpdateFCMTokenTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        updateFCMTokenTask.execute();
    }

    public static void performRegistration(JSONObject postData, final RegistrationListener listener) {

        RegistrationTask registrationTask = new RegistrationTask(postData);
        registrationTask.setRegistrationTaskListener(new RegistrationTask.RegistrationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(AuthBean authBean) {
                if (authBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (authBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(authBean);
                    } else if (authBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(authBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        registrationTask.execute();
    }


    /*public static void performOTPSubmit(JSONObject postData, final OTPSubmitListener listener) {

        OTPSubmitTask otpSubmitTask = new OTPSubmitTask(postData);
        otpSubmitTask.setOtpSubmitTaskListener(new OTPSubmitTask.OTPSubmitTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(OTPBean otpBean) {
                if (otpBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (otpBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(otpBean);
                    } else if (otpBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(otpBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        otpSubmitTask.execute();
    }*/

    public static void performLocationSave(JSONObject postData, final LocationSaveListener listener) {

        LocationSaveTask locationSaveTask = new LocationSaveTask(postData);
        locationSaveTask.setLocationTaskListener(new LocationSaveTask.LocationTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(LocationBean locationBean) {
                if (locationBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (locationBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(locationBean);
                    } else if (locationBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(locationBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        locationSaveTask.execute();
    }

    public static void performDriverRating(JSONObject postData, final DriverRatingListener listener) {

        DriverRatingTask driverRatingTask = new DriverRatingTask(postData);
        driverRatingTask.setDriverRatingTaskListener(new DriverRatingTask.DriverRatingTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(DriverRatingBean driverRatingBean) {
                if (driverRatingBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (driverRatingBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(driverRatingBean);
                    } else if (driverRatingBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(driverRatingBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        driverRatingTask.execute();
    }


    public static void performLogin(JSONObject postData, final LoginListener listener) {

        LoginTask loginTask = new LoginTask(postData);
        loginTask.setLoginTaskListener(new LoginTask.LoginTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(AuthBean authBean) {
                if (authBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (authBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(authBean);
                    } else if (authBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(authBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        loginTask.execute();
    }

    public static void performEditProfile(JSONObject postData, List<String> fileList, final EditProfileListener listener) {

        EditProfileTask editProfileTask = new EditProfileTask(postData, fileList);
        editProfileTask.setEditProfileTaskListener(new EditProfileTask.EditProfileTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(UserBean userBean) {
                if (userBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (userBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(userBean);
                    } else if (userBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(userBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        editProfileTask.execute();
    }

    public static void performFreeRide(JSONObject postData, final FreeRideListener listener) {

        FreeRideTask freeRideTask = new FreeRideTask(postData);
        freeRideTask.setFreeRideTaskListener(new FreeRideTask.FreeRideTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(FreeRideBean freeRideBean) {
                if (freeRideBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (freeRideBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(freeRideBean);
                    } else if (freeRideBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(freeRideBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        freeRideTask.execute();
    }

    public static void performOTPResendCode(JSONObject postData, final OTPResendCodeListener listener) {

        OTPResendCodeTask otpResendCodeTask = new OTPResendCodeTask(postData);
        otpResendCodeTask.setOtpResendTaskListener(new OTPResendCodeTask.OTPResendTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
            }
        });
        otpResendCodeTask.execute();
    }

    public static void fetchUserInfo(JSONObject postData, String userID, final UserInfoListener listener) {

        UserInfoTask userInfoTask = new UserInfoTask(postData);
        userInfoTask.setUserInfoTaskListener(new UserInfoTask.UserInfoTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(UserBean userBean) {
                if (userBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (userBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(userBean);
                    } else if (userBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(userBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        userInfoTask.execute();
    }

    public static void fetchPromoCode(HashMap<String, String> urlParams, final PromoCodeListener listener) {

        PromoCodeTask promoCodeTask = new PromoCodeTask(urlParams);
        promoCodeTask.setPromoCodeTaskListener(new PromoCodeTask.PromoCodeTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(PromoCodeBean promoCodeBean) {
                if (promoCodeBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (promoCodeBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(promoCodeBean);
                    } else if (promoCodeBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(promoCodeBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        promoCodeTask.execute();
    }

    public static void fetchCarAvailability(JSONObject postData, final CarInfoListener listener) {

        CarInfoTask carInfoTask = new CarInfoTask(postData);
        carInfoTask.setCarInfoTaskListener(new CarInfoTask.CarInfoTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(CarBean carBean) {
                if (carBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (carBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(carBean);
                    } else if (carBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(carBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        carInfoTask.execute();
    }

    public static void fetchLandingPageDetails(JSONObject postData, final LandingPageListener landingPageListener) {

        LandingPageDetailsTask landingPageDetailsTask = new LandingPageDetailsTask(postData);
        landingPageDetailsTask.setLandingPageDetailsTaskListener(new LandingPageDetailsTask.LandingPageDetailsTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(LandingPageBean landingPageListBean) {
                if (landingPageListBean == null)
                    landingPageListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (landingPageListBean.getStatus().equalsIgnoreCase("Success")) {
                        landingPageListener.onLoadCompleted(landingPageListBean);
                    } else if (landingPageListBean.getStatus().equalsIgnoreCase("Error")) {
                        landingPageListener.onLoadFailed(landingPageListBean.getErrorMsg());
                    } else {
                        landingPageListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                landingPageListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        landingPageDetailsTask.execute();
    }

    public static void fetchTripDetails(HashMap<String, String> urlParams, final TripDetailsListener listener) {

        TripDetailsTask tripDetailsTask = new TripDetailsTask(urlParams);
        tripDetailsTask.setTripDetailsTaskListener(new TripDetailsTask.TripDetailsTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(TripDetailsBean tripDetailsBean) {
                if (tripDetailsBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (tripDetailsBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(tripDetailsBean);
                    } else if (tripDetailsBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(tripDetailsBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        tripDetailsTask.execute();
    }

    public static void fetchTotalFare(JSONObject postData, final TotalFareListener listener) {

        TotalFareTask totalFareTask = new TotalFareTask(postData);
        totalFareTask.setTotalFareTaskListener(new TotalFareTask.TotalFareTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(FareBean fareBean) {
                if (fareBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (fareBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(fareBean);
                    } else if (fareBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(fareBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        totalFareTask.execute();
    }


    /*public static void fetchRecentSearches(HashMap<String, String> urlParams, final RecentSearchListener listener) {

        RecentSearchTask recentSearchTask = new RecentSearchTask(urlParams);
        recentSearchTask.setRecentSearchListener(new RecentSearchTask.RecentSearchListener() {

            @Override
            public void dataDownloadedSuccessfully(RecentSearchBean recentSearchBean) {
                if (recentSearchBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (recentSearchBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(recentSearchBean);
                    } else if (recentSearchBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(recentSearchBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);

            }
        });

        recentSearchTask.execute();
    }*/

    public static void fetchTripList(HashMap<String, String> urlParams, final TripListListener listener) {

        TripListTask tripListTask = new TripListTask(urlParams);
        tripListTask.setTripListTaskListener(new TripListTask.TripListTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(TripListBean tripListBean) {
                if (tripListBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (tripListBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(tripListBean);
                    } else if (tripListBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(tripListBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        tripListTask.execute();
    }

    public static void fetchSavedLocation(JSONObject  postData, final SavedLocationListener listener) {

        SavedLocationTask savedLocationTask = new SavedLocationTask(postData);
        savedLocationTask.setSavedLocationTaskListener(new SavedLocationTask.SavedLocationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(LocationBean locationBean) {
                if (locationBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (locationBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(locationBean);
                    } else if (locationBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(locationBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        savedLocationTask.execute();
    }

    /*public static void fetchLandingPageDetails(HashMap<String, String> urlParams, final LandingPageDetailsListener listener) {

        LandingPageDetailsTask landingPageDetailsTask = new LandingPageDetailsTask(urlParams);
        landingPageDetailsTask.setLandingPageDetailsTaskListener(new LandingPageDetailsTask.LandingPageDetailsTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(LandingPageListBean landingPageListBean) {
                if (landingPageListBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (landingPageListBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(landingPageListBean);
                    } else if (landingPageListBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(landingPageListBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        landingPageDetailsTask.execute();
    }*/


    public static void fetchRequestStatus(HashMap<String, String> urlParams, final RequestStatusListener listener) {

        RequestStatusTask requestStatusTask = new RequestStatusTask(urlParams);
        requestStatusTask.setRequestStatusTaskListener(new RequestStatusTask.RequestStatusTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(RequestBean requestBean) {
                if (requestBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (requestBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(requestBean);
                    } else if (requestBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(requestBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        requestStatusTask.execute();
    }

    public static void fetchRequestStatus(HashMap<String, String> urlParams, final DriverDetailsListener listener) {

        DriverDetailsTask driverdetailsTask = new DriverDetailsTask(urlParams);
        driverdetailsTask.setDriverDetailsTaskListener(new DriverDetailsTask.DriverDetailsTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(DriverBean driverBean) {
                if (driverBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (driverBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(driverBean);
                    } else if (driverBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(driverBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        driverdetailsTask.execute();
    }


    public static void fetchTripCompletionDetails(HashMap<String, String> urlParams, final SuccessListener listener) {

        SuccessDetailsTask successDetailsTask = new SuccessDetailsTask(urlParams);
        successDetailsTask.setSuccessDetailsTaskListener(new SuccessDetailsTask.SuccessDetailsTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(SuccessBean successBean) {
                if (successBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (successBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(successBean);
                    } else if (successBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(successBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        successDetailsTask.execute();

    }

    public static void fetchPolyPoints(HashMap<String, String> urlParams, final PolyPointsListener polyPointsListener) {

        PolyPointsTask polyPointsTask = new PolyPointsTask(urlParams);
        polyPointsTask.setPolyPointsTaskListener(new PolyPointsTask.PolyPointsTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(PolyPointsBean polyPointsBean) {
                if (polyPointsBean == null)
                   // polyPointsListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                polyPointsListener.onLoadFailed("Polypoints bean null problem");
                else {
                    if (polyPointsBean.getStatus().equalsIgnoreCase("Success")) {
                        polyPointsListener.onLoadCompleted(polyPointsBean);
                    } else if (polyPointsBean.getStatus().equalsIgnoreCase("Error")) {
                        polyPointsListener.onLoadFailed(polyPointsBean.getErrorMsg());
                    } else {
                       // polyPointsListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                        polyPointsListener.onLoadFailed("Load fail  problem");
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
               // polyPointsListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                polyPointsListener.onLoadFailed("Download fail  problem");
            }
        });
        polyPointsTask.execute();
    }

    public static void performTripFeedback(JSONObject postData, final TripFeedbackListener tripFeedbackListener) {

        TripFeedbackTask tripFeedbackTask = new TripFeedbackTask(postData);
        tripFeedbackTask.setTripFeedbackTaskListener(new TripFeedbackTask.TripFeedbackTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(TripFeedbackBean tripFeedbackBean) {
                if (tripFeedbackBean == null)
                    tripFeedbackListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (tripFeedbackBean.getStatus().equalsIgnoreCase("Success")) {
                        tripFeedbackListener.onLoadCompleted(tripFeedbackBean);
                    } else if (tripFeedbackBean.getStatus().equalsIgnoreCase("Error")) {
                        tripFeedbackListener.onLoadFailed(tripFeedbackBean.getErrorMsg());
                    } else {
                        tripFeedbackListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                tripFeedbackListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        tripFeedbackTask.execute();
    }

    public static void performTripCancellation(JSONObject postData, final TripCancellationListener tripCancellationListener) {

        TripCancellationTask tripCancellationTask = new TripCancellationTask(postData);
        tripCancellationTask.setTripCancellationTaskListener(new TripCancellationTask.TripCancellationTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(TripCancellationBean tripCancellationBean) {
                if (tripCancellationBean == null)
                    tripCancellationListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (tripCancellationBean.getStatus().equalsIgnoreCase("Success")) {
                        tripCancellationListener.onLoadCompleted(tripCancellationBean);
                    } else if (tripCancellationBean.getStatus().equalsIgnoreCase("Error")) {
                        tripCancellationListener.onLoadFailed(tripCancellationBean.getErrorMsg());
                    } else {
                        tripCancellationListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                tripCancellationListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        tripCancellationTask.execute();
    }

    public static void performRequestRide(JSONObject postData, final RequestRideListener requestRideListener) {

        RequestRideTask requestRideTask = new RequestRideTask(postData);
        requestRideTask.setRequestRideTaskListener(new RequestRideTask.RequestRideTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(RequestBean requestBean) {
                if (requestBean == null)
                    requestRideListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (requestBean.getStatus().equalsIgnoreCase("Success")) {
                        requestRideListener.onLoadCompleted(requestBean);
                    } else if (requestBean.getStatus().equalsIgnoreCase("Error")) {
                        requestRideListener.onLoadFailed(requestBean.getErrorMsg());
                    } else {
                        requestRideListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                requestRideListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });
        requestRideTask.execute();
    }

    public static void performNewPassword(JSONObject postData, final BasicListener basicListener) {

        NewPasswordTask newPasswordTask = new NewPasswordTask(postData);
        newPasswordTask.setNewPasswordTaskListener(new NewPasswordTask.NewPasswordTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        basicListener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        basicListener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        newPasswordTask.execute();
    }

    public static void performRequestCancel(JSONObject postData, final BasicListener basicListener) {

        RequestCancelTask requestCancelTask = new RequestCancelTask(postData);
        requestCancelTask.setRequestCancelTaskListener(new RequestCancelTask.RequestCancelTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        basicListener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        basicListener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        requestCancelTask.execute();
    }

    public static void performMobileAvailabilityCheck(JSONObject postData, final BasicListener basicListener) {

        MobileAvailabilityCheckTask mobileAvailabilityCheckTask = new MobileAvailabilityCheckTask(postData);
        mobileAvailabilityCheckTask.setMobileAvailabilityCheckTaskListener(
                new MobileAvailabilityCheckTask.MobileAvailabilityCheckTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        basicListener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        basicListener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        mobileAvailabilityCheckTask.execute();
    }

    public static void performRequestTriggering(JSONObject postData, final BasicListener basicListener) {

        RequestTriggeringTask requestTriggeringTask = new RequestTriggeringTask(postData);
        requestTriggeringTask.setRequestTriggeringTaskListener(new RequestTriggeringTask.RequestTriggeringTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(BasicBean basicBean) {
                if (basicBean == null)
                    basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (basicBean.getStatus().equalsIgnoreCase("Success")) {
                        basicListener.onLoadCompleted(basicBean);
                    } else if (basicBean.getStatus().equalsIgnoreCase("Error")) {
                        basicListener.onLoadFailed(basicBean.getErrorMsg());
                    } else {
                        basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                basicListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        requestTriggeringTask.execute();
    }

    public static void fetchAppStatus(JSONObject postData, final AppStatusListener appStatusListener) {

        AppStatusTask appStatusTask = new AppStatusTask(postData);
        appStatusTask.setAppStatusTaskListener(new AppStatusTask.AppStatusTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(DriverBean driverBean) {
                if (driverBean == null)
                    appStatusListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (driverBean.getStatus().equalsIgnoreCase("Success")) {
                        appStatusListener.onLoadCompleted(driverBean);
                    } else if (driverBean.getStatus().equalsIgnoreCase("Error")) {
                        appStatusListener.onLoadFailed(driverBean.getErrorMsg());
                    } else {
                        appStatusListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                appStatusListener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
            }
        });

        appStatusTask.execute();
    }
}



    /*public static void fetchSearchResults(HashMap<String, String> urlParams, final SearchResultsListener listener){

        SearchResultsTask searchResultsTask = new SearchResultsTask(urlParams);
        searchResultsTask.setSearchResultsTaskListener(new SearchResultsTask.SearchResultsTaskListener() {

            @Override
            public void dataDownloadedSuccessfully(SearchResultsBean searchResultsBean) {
                if (searchResultsBean == null)
                    listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                else {
                    if (searchResultsBean.getStatus().equalsIgnoreCase("Success")) {
                        listener.onLoadCompleted(searchResultsBean);
                    } else if (searchResultsBean.getStatus().equalsIgnoreCase("Error")) {
                        listener.onLoadFailed(searchResultsBean.getErrorMsg());
                    } else {
                        listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);
                    }
                }
            }

            @Override
            public void dataDownloadFailed() {
                listener.onLoadFailed(AppConstants.WEB_ERROR_MSG);

            }
        });

        searchResultsTask.execute();
        }
    }*/

