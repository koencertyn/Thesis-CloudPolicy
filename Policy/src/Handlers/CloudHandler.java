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

    private CloudPolicyInterpreter cloudInterpreter;
    private RequestInterpreter requestInterpreter;
    private RequestController requestController;

    public CloudHandler(RequestController requestController,
                          RequestInterpreter requestInterpreter, CloudPolicyInterpreter cloudInterpreter){
        setCloudInterpreter(cloudInterpreter);
        setRequestController(requestController);
        setRequestInterpreter(requestInterpreter);
    }

    public void handle(Request request){
        ArrayList<Hashtable<String,String>> cloudSelection = cloudInterpreter.getAttributes();
        Hashtable<String,String> requestInfo = requestInterpreter.getAttributes(request.getValue());
        handlePriority(cloudSelection,requestInfo.get("priority"));
        handleEncryption(cloudSelection,requestInfo.get("encryption"));
        handleData(cloudSelection, requestInfo.get("data")); 
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

    public ArrayList<Hashtable<String,String>> handleEncryption(ArrayList<Hashtable<String,String>> cloudSelection,
                                                              String encryption){
        for(Hashtable<String,String> entry : cloudSelection){
            if(! hasEncryption(encryption, entry)){
                cloudSelection.remove(entry);
            }
        }
        return cloudSelection;
    }

    public ArrayList<Hashtable<String,String>> handleData(ArrayList<Hashtable<String,String>> cloudSelection,
                                                                String data){
        for(Hashtable<String,String> entry : cloudSelection){
            if(! hasData(data, entry)){
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
            if( entry.equals("yes"))
                return true;
            else if( entry.equals("no"))
                return false;
            else
                throw new IllegalArgumentException("Unexpected priority parameter given.");
        }
        return true;
    }

    public boolean hasData(String data, Hashtable<String, String> entry){
        /*
            Currently java 1.6 does not support switch case with strings.  1.7 does.
         */
        if (data.equals("basic")) {
            return true;
        } else if (data.equals("average")) {
            if(entry.equals("basic"))
                return false;
            return true;
        } else if (data.equals("extended")) {
            return entry.equals("extended");
        } else {
            throw new IllegalArgumentException("Unexpected priority parameter given.");
        }
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
