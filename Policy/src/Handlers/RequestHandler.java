package Handlers;

import Controllers.RequestController;
import Interpreters.LocalConditionInterpreter;
import Interpreters.RequestInterpreter;
import Requests.Request;

import java.util.Hashtable;

/**
 * Created by koencertyn on 26/10/14.
 */
public class RequestHandler {

    private final int shortJobTime = 120;

    private Request request;

    private LocalConditionInterpreter localConditions;
    private RequestInterpreter requestInterpreter;
    private RequestController requestController;

    public RequestHandler(Request request, LocalConditionInterpreter localConditions, RequestController requestController,
                          RequestInterpreter requestInterpreter){
        setRequest(request);
        setLocalConditions(localConditions);
        setRequestController(requestController);
        setRequestInterpreter(requestInterpreter);

    }

    public void handle(){
        if(isShortJob()){
            if(localConditions.getLocalLoad() < 75)
                requestController.addLocalJob(getRequest());
            else{
                requestController.addCloudJob(getRequest());
            }
        } else {
            Hashtable<String, String> requestInformation = getRequestInterpreter().getAttributes(getRequest().getValue());
            if(requestInformation.get("priority") == "fast"){
                if(requestInformation.get("data") == "low"){
                    if(localConditions.getLocalLoad() < 40)
                        requestController.addLocalJob(getRequest());
                    else if (localConditions.getLocalLoad() >= 50 && localConditions.getLocalLoad() < 70) {
                        requestController.bootCloud();
                        requestController.addLocalJob(getRequest());
                    } else {
                        requestController.addCloudJob(getRequest());
                    }
                } else {
                    if(localConditions.getLocalLoad() < 40 && getRequest().getEstimatedDataSpace() < 100)
                        requestController.addLocalJob(getRequest());
                    else{
                        requestController.addCloudJob(getRequest());
                    }
                }
            } else if (requestInformation.get("priority") == "balanced"){
                if(requestInformation.get("data") == "low"){
                    if(localConditions.getLocalLoad() < 70)
                        requestController.addLocalJob(getRequest());
                    else if (localConditions.getLocalLoad() >= 70 && localConditions.getLocalLoad() < 90) {
                        requestController.bootCloud();
                        requestController.addLocalJob(getRequest());
                    } else {
                        requestController.addCloudJob(getRequest());
                    }
                } else {
                    if(localConditions.getLocalLoad() < 40 && getRequest().getEstimatedDataSpace() < 100)
                        requestController.addLocalJob(getRequest());
                    else{
                        requestController.addCloudJob(getRequest());
                    }
                }
            } else {
                requestController.addToLocalQueue(getRequest());
            }
        }

    }

    private boolean isShortJob(){
        return getRequest().getEstimatedCalculationTime() < this.shortJobTime;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public LocalConditionInterpreter getLocalConditions() {
        return localConditions;
    }

    public void setLocalConditions(LocalConditionInterpreter localConditions) {
        this.localConditions = localConditions;
    }

    public RequestController getRequestController() {
        return requestController;
    }

    public void setRequestController(RequestController requestController) {
        this.requestController = requestController;
    }

    public RequestInterpreter getRequestInterpreter() {
        return requestInterpreter;
    }

    public void setRequestInterpreter(RequestInterpreter requestInterpreter) {
        this.requestInterpreter = requestInterpreter;
    }
}
