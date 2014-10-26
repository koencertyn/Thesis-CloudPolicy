package Controllers;

import Handlers.RequestHandler;
import Requests.Request;

/**
 * Created by koencertyn on 25/10/14.
 */
public class RequestController {

    public void handleRequest(Request request){
        RequestHandler handler = new RequestHandler(request);
        handler.handle();
    }
}
