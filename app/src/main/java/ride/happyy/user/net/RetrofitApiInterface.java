package ride.happyy.user.net;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ride.happyy.user.model.Car;
import ride.happyy.user.model.MyNotification;
import ride.happyy.user.model.OnedayRequestModel;
import ride.happyy.user.model.OnedayRequestResponse;
import ride.happyy.user.model.OutOfDhakaFare;
import ride.happyy.user.model.OutOfDhakaServiceModel;
import ride.happyy.user.model.OutofDhakaFareCalModel;
import ride.happyy.user.model.OutofDhakaServerresponse;
import ride.happyy.user.model.ServerResponse;
import ride.happyy.user.model.User;

public interface RetrofitApiInterface {
    @POST("server_side_code.php")
    Call<ServerResponse> getUserValidity(@Body User userLoginCredential);

    @GET("outofdhakacervice.php")
    Call<ServerResponse> getJoke(@Query("user_id") String userId);

    @POST("outofdhakacervice.php")
    Call<ServerResponse> sendOutOfdhakaRequest(@Body OutOfDhakaServiceModel outOfDhakaService);
    @GET("cars_location.php")
    Call<ArrayList<Car>> getCarsLocation(@Query("request_type") String req_type);
    @GET("outofdhaka_fare.php")
    Call<OutOfDhakaFare> getOutOfDhakaFare(@Query("district_name") String districtName,@Query("district_name_from") String districtNameFrom);
    @GET("cars_location.php")
    Call<ArrayList<Car>> getAllLocations(@Query("request_type") String request_type);
    @GET("get_all_message.php")
    Call<ArrayList<MyNotification>> getMyAllMessage(@Query("passenger_phone") String phone);

    @POST("outofdhaka_fare.php")
    Call<OutOfDhakaFare> sendOutOfdhakaFareCal(@Body OutofDhakaFareCalModel outOfDhakaFareCal);

    //for noah and hiace oneday request
    @POST("onday_request_handle.php")
    Call<OnedayRequestResponse> onedayRequest(@Body OnedayRequestModel onedayRequestModel);
}



