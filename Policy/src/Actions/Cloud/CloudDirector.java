package Actions.Cloud;

import java.lang.reflect.Method;

/**
 * Created by koencertyn on 15/11/14.
 */
public class CloudDirector {

    public void executeAction(String cloud){
        invokeMethod(cloud,"executeAction");
    }

    public void deployCloud(String cloud){
        invokeMethod(cloud,"deployCloud");
    }

    private void invokeMethod(String cloud, String methodName){
        try{
            Class cls = Class.forName("Actions.Cloud."+cloud+"Action");
            Object obj = cls.newInstance();

            //call the executeAction method
            Method method = cls.getDeclaredMethod(methodName);
            method.invoke(obj, null);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
