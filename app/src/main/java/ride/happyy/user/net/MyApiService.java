package ride.happyy.user.net;

import java.util.ArrayList;

import ride.happyy.user.model.Car;
import ride.happyy.user.model.MyNotification;
import ride.happyy.user.model.OnedayRequestModel;
import ride.happyy.user.model.OnedayRequestResponse;
import ride.happyy.user.model.OutOfDhakaFare;
import ride.happyy.user.model.OutOfDhakaServiceModel;
import ride.happyy.user.model.OutofDhakaFareCalModel;
import ride.happyy.user.model.OutofDhakaServerresponse;
import ride.happyy.user.model.User;

public interface MyApiService {
    void userValidityCheck(User userLoginCredential, ResponseCallback<String> callback);
    void getJokeFromServer(String userId, ResponseCallback<String> callback);
    void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService,ResponseCallback<String> callback);
    void getCarLocation(String requestType, ResponseCallback<ArrayList<Car>> responseCallback);
    void getOutOfDhakaFare(String districNameFrom,String districNameTo, ResponseCallback<OutOfDhakaFare> responseCallback);
    void getAllLocations(String request_type,ResponseCallback<ArrayList<Car>> responseCallback);
    void getMyAllMessage(String phone, ResponseCallback<ArrayList<MyNotification>> responseCallback);
    void getOutOfDhakaFareCal(OutofDhakaFareCalModel fareCalModel, ResponseCallback<OutOfDhakaFare> responseCallback);
    void sendOndeDayReq(OnedayRequestModel onedayRequestModel, ResponseCallback<OnedayRequestResponse> responseCallback);
}
