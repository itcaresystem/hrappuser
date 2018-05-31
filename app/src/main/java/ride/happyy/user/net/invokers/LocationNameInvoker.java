package ride.happyy.user.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.LocationNameParser;
import ride.happyy.user.net.utils.WSConstants;

public class LocationNameInvoker extends BaseInvoker {


    public LocationNameInvoker() {
        super();
        // TODO Auto-generated constructor stub
    }


    public LocationNameInvoker(HashMap<String, String> urlParams, JSONObject postData) {
        super(urlParams, postData);
    }


    public String invokeLocationNameWS() {

        System.out.println("URL PARAMS>>>>>>>" + urlParams);

        WebConnector webConnector;
        //	webConnector= new WebConnector(new StringBuilder(ServiceNames.LOCATION_NAME), WSConstants.PROTOCOL_HTTP, null,postData);
        webConnector = new WebConnector(new StringBuilder(ServiceNames.LOCATION_NAME), WSConstants.PROTOCOL_HTTP, urlParams, null);

        System.out.println("WebConnector>>>>>" + webConnector);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//		String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(false);
        String address = null;
        if (wsResponseString.equals("")) {
            /*authBean=new AuthBean();
			authBean.setWebError(true);*/
            return address = null;
        } else {
            address = "";
            LocationNameParser locationNameParser = new LocationNameParser();
            address = locationNameParser.parseLocationNameResponse(wsResponseString);
            return address;
        }
    }


}
