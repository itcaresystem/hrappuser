package ride.happyy.user.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.LandingPageBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.LandingPageDetailsParser;
import ride.happyy.user.net.utils.WSConstants;

public class LandingPageDetailsInvoker extends BaseInvoker {

    public LandingPageDetailsInvoker(HashMap<String, String> urlParams,
                                     JSONObject postData) {
        super(urlParams, postData);
    }

    public LandingPageBean invokeLandingPageDetailsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.CAR_TYPES), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants. PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        LandingPageBean landingPageBean = null;
        if (wsResponseString.equals("")) {
            return landingPageBean = null;
        } else {
            landingPageBean = new LandingPageBean();
            LandingPageDetailsParser landingPageDetailsParser = new LandingPageDetailsParser();
            landingPageBean = landingPageDetailsParser.parseLandingPageDetailsResponse(wsResponseString);
            return landingPageBean;
        }
    }
}
