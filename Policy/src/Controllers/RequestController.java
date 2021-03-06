package Controllers;

import Handlers.CloudHandler;
import Handlers.RequestHandler;
import Interpreters.CloudPolicyInterpreter;
import Interpreters.LocalConditionInterpreter;
import Interpreters.RequestInterpreter;
import Requests.Request;
import Test.LocalConfig;

/**
 * Created by koencertyn on 25/10/14.
 */
public class RequestController {

    private LocalConditionInterpreter localConditions;
    private RequestInterpreter requestInterpreter;

    private CloudHandler cloudHandler;

    public RequestController(LocalConditionInterpreter localConditions, RequestInterpreter requestInterpreter){
        setCloudHandler(cloudHandler);
        setLocalConditions(localConditions);
        setRequestInterpreter(requestInterpreter);
    }

    private LocalConfig local;

    public RequestController(LocalConditionInterpreter localConditions, RequestInterpreter requestInterpreter, LocalConfig local){
        CloudPolicyInterpreter interpreter = new CloudPolicyInterpreter();
        CloudHandler handler = new CloudHandler(this,requestInterpreter,interpreter);
        setCloudHandler(handler);
        setLocalConditions(localConditions);
        setRequestInterpreter(requestInterpreter);
        this.local = local;
    }


    public void handleRequest(Request request){
        RequestHandler handler = new RequestHandler(request, localConditions, this, requestInterpreter);
        handler.handle();
    }

    public void addLocalJob(Request request) {
        System.out.println("adding localjob");
        local.addJob(request);
    }

    public void addCloudJob(Request request) {
        System.out.println(request.getValue());
        System.out.println("adding cloudjob");
        getCloudHandler().handle(request);
    }

    public void bootCloud() {
        System.out.println("booting cloud");

    }

    public CloudHandler getCloudHandler() {
        return cloudHandler;
    }

    public void setCloudHandler(CloudHandler cloudHandler) {
        this.cloudHandler = cloudHandler;
    }


    private LocalConditionInterpreter getLocalConditions() {
        return localConditions;
    }

    private void setLocalConditions(LocalConditionInterpreter localConditions) {
        this.localConditions = localConditions;
    }

    private RequestInterpreter getRequestInterpreter() {
        return requestInterpreter;
    }

    private void setRequestInterpreter(RequestInterpreter requestInterpreter) {
        this.requestInterpreter = requestInterpreter;
    }

    public void addToLocalQueue(Request request) {
    }
}
