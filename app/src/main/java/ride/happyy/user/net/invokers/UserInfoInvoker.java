package ride.happyy.user.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.UserBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.UserInfoParser;
import ride.happyy.user.net.utils.WSConstants;

public class UserInfoInvoker extends BaseInvoker {

    public UserInfoInvoker() {
        super();
    }

    public UserInfoInvoker(HashMap<String, String> urlParams,
                          JSONObject postData) {
        super(urlParams, postData);
    }

    public UserBean invokeUserInfoWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.USER_INFO), WSConstants.PROTOCOL_HTTP, null, postData);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToPOST_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        UserBean userBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
			registerBean.setWebError(true);*/
            return userBean = null;
        } else {
            userBean = new UserBean();
            UserInfoParser userInfoParser = new UserInfoParser();
            userBean = userInfoParser.parseUserInfoResponse(wsResponseString);
            return userBean;
        }
    }
}
