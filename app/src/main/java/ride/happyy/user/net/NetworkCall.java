package ride.happyy.user.net;

import com.google.android.gms.common.logging.Logger;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    public void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService, final ResponseCallback<String> callback) {
        RetrofitApiInterface retrofitApiInterface   = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.sendOutOfdhakaRequest(outOfDhakaService);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse validity = response.body();
                if(validity!=null){
                    if(validity.isSuccess())
                        callback.onSuccess(validity.getMessage());

                    else
                        callback.onError(new Exception(validity.getMessage()));
                }
                else
                    callback.onError(new Exception(response.message()));
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void getCarLocation(String requestType, final ResponseCallback<ArrayList<Car>> responseCallback) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ArrayList<Car>> call = retrofitApiInterface.getCarsLocation(requestType);
        call.enqueue(new Callback<ArrayList<Car>>() {
            @Override
            public void onResponse(Call<ArrayList<Car>> call, Response<ArrayList<Car>> response) {
                ArrayList<Car> carArrayList = response.body();
                if (carArrayList!=null){
                    responseCallback.onSuccess(carArrayList);
                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Car>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getOutOfDhakaFare(String districNameFrom,String districNameTo, final ResponseCallback<OutOfDhakaFare> responseCallback) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<OutOfDhakaFare> call =retrofitApiInterface.getOutOfDhakaFare(districNameFrom,districNameTo);
        call.enqueue(new Callback<OutOfDhakaFare>() {
            @Override
            public void onResponse(Call<OutOfDhakaFare> call, Response<OutOfDhakaFare> response) {
                OutOfDhakaFare outOfDhakaFare =response.body();
                if (outOfDhakaFare!=null){
                    responseCallback.onSuccess(outOfDhakaFare);
                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OutOfDhakaFare> call, Throwable t) {

            }
        });
    }

    @Override
    public void getAllLocations(String request_type, final ResponseCallback<ArrayList<Car>> responseCallback) {
        RetrofitApiInterface retrofitApiInterface =RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ArrayList<Car>> call =retrofitApiInterface.getAllLocations(request_type);
        call.enqueue(new Callback<ArrayList<Car>>() {
            @Override
            public void onResponse(Call<ArrayList<Car>> call, Response<ArrayList<Car>> response) {
                ArrayList<Car> carArrayList = response.body();
                if(carArrayList!=null){
                    responseCallback.onSuccess(carArrayList);

                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Car>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getMyAllMessage(String phone, final ResponseCallback<ArrayList<MyNotification>> responseCallback) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ArrayList<MyNotification>> call = retrofitApiInterface.getMyAllMessage(phone);
        call.enqueue(new Callback<ArrayList<MyNotification>>() {
            @Override
            public void onResponse(Call<ArrayList<MyNotification>> call, Response<ArrayList<MyNotification>> response) {
                ArrayList<MyNotification> myNotifications =response.body();
                if(myNotifications!=null){
                    responseCallback.onSuccess(myNotifications);

                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyNotification>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getOutOfDhakaFareCal(OutofDhakaFareCalModel fareCalModel, final ResponseCallback<OutOfDhakaFare> responseCallback) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<OutOfDhakaFare> call =retrofitApiInterface.sendOutOfdhakaFareCal(fareCalModel);
        call.enqueue(new Callback<OutOfDhakaFare>() {
            @Override
            public void onResponse(Call<OutOfDhakaFare> call, Response<OutOfDhakaFare> response) {
                OutOfDhakaFare outOfDhakaFare =response.body();
                if (outOfDhakaFare!=null){
                    responseCallback.onSuccess(outOfDhakaFare);
                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OutOfDhakaFare> call, Throwable t) {

            }
        });
    }

    @Override
    public void sendOndeDayReq(OnedayRequestModel onedayRequestModel, final ResponseCallback<OnedayRequestResponse> responseCallback) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<OnedayRequestResponse> call = retrofitApiInterface.onedayRequest(onedayRequestModel);
        call.enqueue(new Callback<OnedayRequestResponse>() {
            @Override
            public void onResponse(Call<OnedayRequestResponse> call, Response<OnedayRequestResponse> response) {
                OnedayRequestResponse onedayRequestResponse = response.body();
                if (onedayRequestResponse!=null){
                    responseCallback.onSuccess(onedayRequestResponse);
                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<OnedayRequestResponse> call, Throwable t) {

            }
        });
    }

}
