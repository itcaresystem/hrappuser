package ride.happyy.user.net.parsers;


import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ride.happyy.user.app.App;
import ride.happyy.user.model.CarBean;
import ride.happyy.user.model.LandingPageBean;

public class LandingPageDetailsParser {

    private static final String TAG = "LPDParser";

    public LandingPageBean parseLandingPageDetailsResponse(String wsResponseString) {

        LandingPageBean landingPageBean = new LandingPageBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            landingPageBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                landingPageBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                landingPageBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        landingPageBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    landingPageBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    landingPageBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    landingPageBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                landingPageBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    landingPageBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    landingPageBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        landingPageBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                landingPageBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                landingPageBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONArray dataArray = null;
                dataArray = jsonObj.optJSONArray("data");
                ArrayList<CarBean> carList = new ArrayList<>();

                if (dataArray != null) {

                    JSONObject carObj;
                    for (int i = 0; i < dataArray.length(); i++) {
                        carObj = dataArray.optJSONObject(i);

                        if (carObj != null) {

                            CarBean carBean = new CarBean();

                            if (carObj.has("car_ID")) {
                                carBean.setCarID(carObj.optString("car_ID"));
                            }
                            if (carObj.has("car_name")) {
                                carBean.setCarName(carObj.optString("car_name"));
                            }
                            if (carObj.has("car_image")) {
                                carBean.setCarImage(App.getImagePath(carObj.optString("car_image")));
                            }
                            if (carObj.has("eta_time")) {
                                carBean.setMinTime(carObj.optString("eta_time"));
                            }
                            if (carObj.has("min_fare")) {
                                carBean.setMinFare(carObj.optString("min_fare"));
                            }
                            if (carObj.has("max_size")) {
                                carBean.setMaxSize(carObj.optString("max_size"));
                               // carBean.setVehicle_lat(carObj.optDouble("vehicle_lat"));
                            }

                            if (carObj.has("id")) {
                                carBean.setId(carObj.optString("id"));
                            }
                            if (carObj.has("vehicle_type")) {
                                carBean.setVehicle_type(carObj.optString("vehicle_type"));
                            }

                            if (carObj.has("service_type")) {
                                carBean.setService_type(carObj.optString("service_type"));
                            }
                            if (carObj.has("driver_id")) {
                                carBean.setDriver_id(carObj.optString("driver_id"));
                            }
                            if (carObj.has("phone")) {
                                carBean.setPhone(carObj.optString("phone"));
                            }

                            if (carObj.has("vehicle_lat")) {
                               // carBean.setMaxSize(carObj.optString("vehicle_lat"));
                                carBean.setVehicle_lat(carObj.optDouble("vehicle_lat"));
                            }
                            if (carObj.has("vehicle_lon")) {
                                carBean.setVehicle_lon(carObj.optDouble("vehicle_lon"));
                            }

                            if (carObj.has("is_verified")) {
                                carBean.set_verified(carObj.optBoolean("is_verified"));
                            }
                            if (carObj.has("is_active")) {
                                carBean.set_active(carObj.optBoolean("is_active"));
                            }
                            if (carObj.has("is_online")) {
                                carBean.set_online(carObj.optBoolean("is_online"));
                            }
                            if (carObj.has("trip_status")) {
                                carBean.setTrip_status(carObj.optBoolean("trip_status"));
                            }
                            if (carObj.has("on_trip")) {
                                carBean.setOn_trip(carObj.optBoolean("on_trip"));
                            }
                            if (carObj.has("is_bike_service")) {
                                carBean.set_bike_service(carObj.optBoolean("is_bike_service"));
                            }
                            if (carObj.has("is_cng_service")) {
                                carBean.set_cng_service(carObj.optBoolean("is_cng_service"));
                            }
                            if (carObj.has("is_car_service")) {
                                carBean.set_car_service(carObj.optBoolean("is_car_service"));
                            }

                            if (carObj.has("is_ambulance_service")) {
                                carBean.set_ambulance_service(carObj.optBoolean("is_ambulance_service"));
                            }
                            if (carObj.has("is_car_premio_service")) {
                                carBean.set_car_premio_service(carObj.optBoolean("is_car_premio_service"));
                            }
                            if (carObj.has("is_car_noah_service")) {
                                carBean.set_car_noah_service(carObj.optBoolean("is_car_noah_service"));
                            }
                            if (carObj.has("is_car_hiace_service")) {
                                carBean.set_car_noah_service(carObj.optBoolean("is_car_hiace_service"));
                            }


                            Log.i(TAG, "parseLandingPageDetailsResponse: " + new Gson().toJson(carBean));

                            carList.add(carBean);

                        }
                    }
                }
                landingPageBean.setCars(carList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return landingPageBean;
    }
}
