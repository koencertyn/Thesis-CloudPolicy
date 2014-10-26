package Interpreters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by koencertyn on 25/10/14.
 */
public class CloudPolicyInterpreter {

    private final String[] attributes = {"platform", "encryption", "log", "datamodel","processing","deployment","cost"};

    public Hashtable<String,Hashtable<String,String>> getAttributes(){

        Hashtable<String,Hashtable<String,String>> result = new Hashtable<String,Hashtable<String,String>>();
        Hashtable<String,String> attributes = new Hashtable<String,String>();

        String cloudConfig = readFile("/Users/koencertyn/thesis/Thesis-CloudPolicy/Policy/cloudConfig.conf").toLowerCase();
        int lowerCloud = 0;
        int higherCloud = cloudConfig.length();

        String cloudName = "";
        while(lowerCloud < higherCloud){
            String selectConfig = cloudConfig.substring(lowerCloud,higherCloud);

            int lowerIndex = selectConfig.indexOf( "<cloud>" ) + ("<cloud>").length();
            int higherIndex = selectConfig.indexOf( "</cloud>" );

            lowerCloud = lowerCloud + higherIndex + ("</cloud>").length();

            String cloudInformation = selectConfig.substring(lowerIndex,higherIndex);
            System.out.println(cloudInformation);
            for(String attribute : this.attributes){
                if(attribute.equals("platform"))
                    cloudName = getAttribute(attribute,cloudInformation);
                else
                    attributes.put(attribute,getAttribute(attribute,cloudInformation));
            }
            result.put(cloudName,attributes);
        }

        return result;
    }

    public String getAttribute(String attribute, String request){
        int lowerIndex = request.indexOf( "<"+attribute+">" ) + ("<"+attribute+">").length();
        int higherIndex = request.indexOf( "</"+attribute+">" );
        return request.substring(lowerIndex,higherIndex).replaceAll("\\s+", "");
    }

    public String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
