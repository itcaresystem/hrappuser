package ride.happyy.user.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.LocationBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.LocationSaveParser;
import ride.happyy.user.net.utils.WSConstants;

public class LocationSaveInvoker extends BaseInvoker {

    public LocationSaveInvoker() {
        super();
    }

    public LocationSaveInvoker(HashMap<String, String> urlParams,
                               JSONObject postData) {
        super(urlParams, postData);
    }

    public LocationBean invokeLocationSaveWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.LOCATION_SAVE), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        LocationBean locationBean = null;
        if (wsResponseString.equals("")) {

            return locationBean = null;

        } else {
            locationBean = new LocationBean();
            LocationSaveParser locationSaveParser = new LocationSaveParser();
            locationBean = locationSaveParser.parseLocationSaveResponse(wsResponseString);
            return locationBean;
        }
    }
}
