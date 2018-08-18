package ride.happyy.user.net;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ride.happyy.user.model.OutOfDhakaServiceModel;
import ride.happyy.user.model.OutofDhakaServerresponse;
import ride.happyy.user.model.ServerResponse;
import ride.happyy.user.model.User;

public interface RetrofitApiInterface {
    @POST("retrofit_get_post/server_side_code.php")
    Call<ServerResponse> getUserValidity(@Body User userLoginCredential);

    @GET("retrofit_get_post/server_side_code.php")
    Call<ServerResponse> getJoke(@Query("user_id") String userId);

    @POST("outofdhakacervice")
    Call<OutofDhakaServerresponse> sendOutOfdhakaRequest(@Body OutOfDhakaServiceModel outOfDhakaService);
}
