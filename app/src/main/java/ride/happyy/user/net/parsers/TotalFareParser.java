package ride.happyy.user.net.parsers;


import org.json.JSONException;
import org.json.JSONObject;

import ride.happyy.user.model.FareBean;

public class TotalFareParser {

    public FareBean parseFareInfoResponse(String wsResponseString) {

        FareBean fareBean = new FareBean();

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(wsResponseString);

            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            fareBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fareBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                fareBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        fareBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        fareBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        fareBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        fareBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    fareBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    fareBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    fareBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("status")) {
                fareBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("notfound"))
                    fareBean.setErrorMsg("Email Not Found");
                if (jsonObj.optString("status").equals("invalid"))
                    fareBean.setErrorMsg("Password Is Incorrect");
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        fareBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
            }

            if (jsonObj.has("error")) {
                fareBean.setErrorMsg(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                fareBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("data")) {

                JSONObject dataJSObject = null;
                dataJSObject = jsonObj.optJSONObject("data");

                if (dataJSObject != null) {
                    try {
                        if (dataJSObject.has("total_fare")) {
                            fareBean.setTotalFare(dataJSObject.optString("total_fare"));
                        }
                        if (dataJSObject.has("estimated_fare")) {
                            fareBean.setEstimatedFare(dataJSObject.optString("estimated_fare"));
                        }
                        if (dataJSObject.has("bikeFare")) {
                            fareBean.setBikeFare(dataJSObject.optString("bikeFare"));
                        }
                        if (dataJSObject.has("cngFare")) {
                            fareBean.setCngFare(dataJSObject.optString("cngFare"));
                        }
                        if (dataJSObject.has("carFare")) {
                            fareBean.setCarFare(dataJSObject.optString("carFare"));
                        }
                        if (dataJSObject.has("ambulanceFare")) {
                            fareBean.setAmbulanceFare(dataJSObject.optString("ambulanceFare"));
                        }
                        if (dataJSObject.has("carHireFare")) {
                            fareBean.setCarHireFare(dataJSObject.optString("carHireFare"));
                        }
                        if (dataJSObject.has("ondayHirePremioFare")) {
                            fareBean.setOndayHirePremioFare(dataJSObject.optString("ondayHirePremioFare"));
                        }
                        if (dataJSObject.has("onedayHireNoahFare")) {
                            fareBean.setOnedayHireNoahFare(dataJSObject.optString("onedayHireNoahFare"));
                        }
                        if (dataJSObject.has("onedayHireHiaceFare")) {
                            fareBean.setOnedayHireHiaceFare(dataJSObject.optString("onedayHireHiaceFare"));
                        }
                   } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return fareBean;
    }
}

