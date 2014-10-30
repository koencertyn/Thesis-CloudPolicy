package Handlers;

import Controllers.RequestController;
import Interpreters.CloudPolicyInterpreter;
import Interpreters.LocalConditionInterpreter;
import Interpreters.RequestInterpreter;
import Logger.CloudRequestLogger;
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

    private CloudRequestLogger logger;

    public CloudHandler(RequestController requestController,
                          RequestInterpreter requestInterpreter, CloudPolicyInterpreter cloudInterpreter){
        setCloudInterpreter(cloudInterpreter);
        setRequestController(requestController);
        setRequestInterpreter(requestInterpreter);
        this.logger = new CloudRequestLogger();
    }

    public void handle(Request request){
        ArrayList<Hashtable<String,String>> cloudSelection = cloudInterpreter.getAttributes();
        Hashtable<String,String> requestInfo = getRequestInterpreter().getAttributes(request.getValue());
        ArrayList<Hashtable<String, String>> tmp1 = handlePriority(cloudSelection,requestInfo.get("priority"));
        ArrayList<Hashtable<String, String>> tmp2 = handleEncryption(tmp1,requestInfo.get("encryption"));
        selectCloud(request,handleData(tmp2, requestInfo.get("data")));
    }

    private void selectCloud(Request request, ArrayList<Hashtable<String, String>> cloudSelection) {
        try {
            logger.log(request, cloudSelection.get(0).get("platform"));
        } catch (Exception e){}
    }

    public ArrayList<Hashtable<String,String>> handlePriority(ArrayList<Hashtable<String,String>> cloudSelection,
                                                                     String priority){

        ArrayList<Hashtable<String,String>> copy = new ArrayList<Hashtable<String,String>>(cloudSelection);
        for(Hashtable<String,String> entry : copy){
            if(! isValidPriority(priority, entry)){
                cloudSelection.remove(entry);
            }
        }
        return cloudSelection;
    }

    public ArrayList<Hashtable<String,String>> handleEncryption(ArrayList<Hashtable<String,String>> cloudSelection,
                                                              String encryption){
        ArrayList<Hashtable<String,String>> copy = new ArrayList<Hashtable<String,String>>(cloudSelection);

        for(Hashtable<String,String> entry : copy){
            if(! hasEncryption(encryption, entry)){
                cloudSelection.remove(entry);
            }
        }
        return cloudSelection;
    }

    public ArrayList<Hashtable<String,String>> handleData(ArrayList<Hashtable<String,String>> cloudSelection,
                                                                String data){
        ArrayList<Hashtable<String,String>> copy = new ArrayList<Hashtable<String,String>>(cloudSelection);

        for(Hashtable<String,String> entry : copy){
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
            if(entry.get("processing").equals("basic") || entry.get("deployment").equals("slow"))
                return false;
            return true;
        } else if (priority.equals("medium")) {
            if(entry.get("processing").equals("basic"))
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
            if( entry.get("encryption").equals("yes"))
                return true;
            else if( entry.get("encryption").equals("no"))
                return false;
            else
                throw new IllegalArgumentException("Unexpected encryption parameter given.");
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
            if(entry.get("datamodel").equals("basic"))
                return false;
            return true;
        } else if (data.equals("extended")) {
            return entry.get("datamodel").equals("extended");
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
