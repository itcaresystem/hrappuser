package ride.happyy.user.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.user.model.SearchResultsBean;
import ride.happyy.user.net.ServiceNames;
import ride.happyy.user.net.WebConnector;
import ride.happyy.user.net.parsers.SearchResultsParser;
import ride.happyy.user.net.utils.WSConstants;

public class SearchResultsInvoker extends BaseInvoker {

    public SearchResultsInvoker(HashMap<String, String> urlParams,
                          JSONObject postData) {
        super(urlParams, postData);
    }

    public SearchResultsBean invokeSearchresultsWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.SEARCH_RESULTS), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        SearchResultsBean searchResultsBean = null;
        if (wsResponseString.equals("")) {
            return searchResultsBean = null;
        } else {
            searchResultsBean = new SearchResultsBean();
            SearchResultsParser searchResultsParser = new SearchResultsParser();
            searchResultsBean = searchResultsParser.parseSearchResultsResponse(wsResponseString);
            return searchResultsBean;
        }
    }
}
