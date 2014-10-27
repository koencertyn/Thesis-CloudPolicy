package Interpreters;

import Requests.Request;
import Test.LocalConfig;

/**
 * Created by koencertyn on 25/10/14.
 */
public class LocalConditionInterpreter {

    public double getLocalLoad(){
        return LocalConfig.getLoad();
    }

    public double getAvailableStorage(){
        return 1;
    }
}
