package ride.happyy.user.net;

import com.google.android.gms.common.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ride.happyy.user.model.OutOfDhakaServiceModel;
import ride.happyy.user.model.OutofDhakaServerresponse;
import ride.happyy.user.model.ServerResponse;
import ride.happyy.user.model.User;


public class NetworkCall implements MyApiService {
    @Override
    public void userValidityCheck(User userLoginCredential, final ResponseCallback<String> userValidityCheckListener) {
      //  Logger.addLogAdapter(new AndroidLogAdapter());

        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.getUserValidity(userLoginCredential);

        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

              //  Logger.d("Network layer. User validity Raw response: " + response.raw());

                ServerResponse validity = response.body();
                if(validity!=null){
                    if(validity.isSuccess())
                        userValidityCheckListener.onSuccess(validity.getMessage());
                    else
                        userValidityCheckListener.onError(new Exception(validity.getMessage()));
                }
                else
                    userValidityCheckListener.onError(new Exception(response.message()));

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                userValidityCheckListener.onError(t);
            }
        });
    }

    @Override
    public void getJokeFromServer(String userId, final ResponseCallback<String> getJokeListener) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.getJoke(userId);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
              //  Logger.d("Network layer. get Joke Raw response: " + response.raw());
                ServerResponse validity = response.body();
                if(validity!=null){
                    if(validity.isSuccess())
                        getJokeListener.onSuccess(validity.getMessage());
                    else
                        getJokeListener.onError(new Exception(validity.getMessage()));
                }
                else
                    getJokeListener.onError(new Exception(response.message()));
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                getJokeListener.onError(t);
            }
        });
    }

    @Override
    public void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService, ResponseCallback<String> callback) {
        RetrofitApiInterface retrofitApiInterface   = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<OutofDhakaServerresponse>    call    = retrofitApiInterface.sendOutOfdhakaRequest(outOfDhakaService);
        call.enqueue(new Callback<OutofDhakaServerresponse>() {
            @Override
            public void onResponse(Call<OutofDhakaServerresponse> call, Response<OutofDhakaServerresponse> response) {
                OutofDhakaServerresponse serverResponse   = response.body();
                if(serverResponse.isSuccess()){


                }
            }

            @Override
            public void onFailure(Call<OutofDhakaServerresponse> call, Throwable t) {

            }
        });
    }
}
