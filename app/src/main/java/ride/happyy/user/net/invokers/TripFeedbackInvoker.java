package ride.happyy.user.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.TripFeedbackBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.TripFeedbackParser;
import ride.happyy.user.net.utils.WSConstants;


public class TripFeedbackInvoker extends BaseInvoker {

    public TripFeedbackInvoker() {
        super();
    }

    public TripFeedbackInvoker(HashMap<String, String> urlParams,
                               JSONObject postData) {
        super(urlParams, postData);
    }

    public TripFeedbackBean invokeTripFeedbackWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_FEEDBACK), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripFeedbackBean tripFeedbackBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripFeedbackBean = null;
        } else {
            tripFeedbackBean = new TripFeedbackBean();
            TripFeedbackParser tripFeedbackParser = new TripFeedbackParser();
            tripFeedbackBean = tripFeedbackParser.parseTripFeedbackResponse(wsResponseString);
            return tripFeedbackBean;
        }
    }
}

