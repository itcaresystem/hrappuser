package ride.happyy.user.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.LocationBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.SavedLocationParser;
import ride.happyy.user.net.utils.WSConstants;

public class SavedLocationInvoker extends BaseInvoker {

    public SavedLocationInvoker() {
        super();
    }

    public SavedLocationInvoker(HashMap<String, String> urlParams,
                                JSONObject postData) {
        super(urlParams, postData);
    }

    public LocationBean invokeDummyWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.SAVED_LOCATION), WSConstants.PROTOCOL_HTTP, null, postData);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToPOST_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        LocationBean locationBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return locationBean = null;
        } else {
            locationBean = new LocationBean();
            SavedLocationParser savedLocationParser = new SavedLocationParser();
            locationBean = savedLocationParser.parseSavedLocationResponse(wsResponseString);
            return locationBean;

        }
    }
}