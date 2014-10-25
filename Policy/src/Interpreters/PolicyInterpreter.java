package Interpreters;

import java.util.Hashtable;

/**
 * Created by koencertyn on 25/10/14.
 */
public class PolicyInterpreter {

    private final String[] attributes = {"platform", "encryption", "log", "datamodel","processing","deployment","cost"};

    public Hashtable<String,String> getAttributes(String requestType){
        Hashtable<String,String> attributes = new Hashtable<String,String>();
        for(String attribute : this.attributes){
            attributes.put(attribute,getAttribute(attribute,requestType));
        }
        return attributes;
    }

    public String getAttribute(String attribute, String request){
        int lowerIndex = request.indexOf( "<"+attribute+">" ) + ("<"+attribute+">").length();
        int higherIndex = request.indexOf( "</"+attribute+">" );
        return request.substring(lowerIndex,higherIndex).replaceAll("\\s+", "");
    }

}
