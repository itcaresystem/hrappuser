package ride.happyy.user.net;

import ride.happyy.user.model.OutOfDhakaServiceModel;
import ride.happyy.user.model.OutofDhakaServerresponse;
import ride.happyy.user.model.User;

public interface MyApiService {
    void userValidityCheck(User userLoginCredential, ResponseCallback<String> callback);
    void getJokeFromServer(String userId, ResponseCallback<String> callback);
    void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService,ResponseCallback<String> callback);
}
