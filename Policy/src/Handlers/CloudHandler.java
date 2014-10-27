package Handlers;

import Controllers.RequestController;
import Interpreters.CloudPolicyInterpreter;
import Interpreters.LocalConditionInterpreter;
import Interpreters.RequestInterpreter;
import Policies.CloudPolicy;
import Requests.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by koencertyn on 27/10/14.
 */
public class CloudHandler {

    private Request request;

    private CloudPolicyInterpreter cloudInterpreter;
    private RequestInterpreter requestInterpreter;
    private RequestController requestController;

    public CloudHandler(Request request, RequestController requestController,
                          RequestInterpreter requestInterpreter, CloudPolicyInterpreter cloudInterpreter){
        setRequest(request);
        setCloudInterpreter(cloudInterpreter);
        setRequestController(requestController);
        setRequestInterpreter(requestInterpreter);
    }

    public void handle(){
        ArrayList<Hashtable<String,String>> cloudSelection = cloudInterpreter.getAttributes();
        Hashtable<String,String> requestInfo = requestInterpreter.getAttributes(getRequest().getValue());
        handlePriority(cloudSelection,requestInfo.get("priority"));
    }

    public ArrayList<Hashtable<String,String>> handlePriority(ArrayList<Hashtable<String,String>> cloudSelection,
                                                                     String priority){
        for(Hashtable<String,String> entry : cloudSelection){
            if(! isValidPriority(priority, entry)){
                cloudSelection.remove(entry);
            }
        }

        return cloudSelection;
    }

    public boolean isValidPriority(String priority, Hashtable<String, String> entry){
        /*
            Currently java 1.6 does not support switch case with strings.  1.7 does.
         */
        if (priority.equals("high")) {
            if(! entry.get("priority").equals("high"))
                return false;
            return true;
        } else if (priority.equals("medium")) {
            if(entry.get("priority").equals("low"))
                return false;
            return true;
        } else if (priority.equals("low")) {
            return true;
        } else {
            throw new IllegalArgumentException("Unexpected priority parameter given.");
        }
    }

    public boolean hasEncryption(String encryption, Hashtable<String, String> entry){
        if (encryption.equals("yes")){
            if( ! entry.equals("yes"))
                return false;
            return true;
        }
        return true;
    }



    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public CloudPolicyInterpreter getCloudInterpreter() {
        return cloudInterpreter;
    }

    public void setCloudInterpreter(CloudPolicyInterpreter cloudInterpreter) {
        this.cloudInterpreter = cloudInterpreter;
    }

    public RequestInterpreter getRequestInterpreter() {
        return requestInterpreter;
    }

    public void setRequestInterpreter(RequestInterpreter requestInterpreter) {
        this.requestInterpreter = requestInterpreter;
    }

    public RequestController getRequestController() {
        return requestController;
    }

    public void setRequestController(RequestController requestController) {
        this.requestController = requestController;
    }

}
