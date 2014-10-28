package Test;

import Controllers.RequestController;
import Interpreters.CloudPolicyInterpreter;
import Interpreters.LocalConditionInterpreter;
import Interpreters.RequestInterpreter;
import Logger.LocalRequestLogger;
import Policies.CloudPolicy;
import Requests.Request;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by koencertyn on 20/10/14.
 */
public class Main {
    public static void main(String args[]){
//        final LocalConfig local = new LocalConfig(40);
//        LocalConditionInterpreter localInt = new LocalConditionInterpreter();
//        RequestInterpreter requestInterpreter = new RequestInterpreter();
//        RequestController controller = new RequestController(localInt, requestInterpreter, local);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Second thread started");
//                while(true) {
//                    try {
//                        local.recalculateJobs();
//                        Thread.sleep(2000);  // wait two seconds
//                    } catch (Exception e) {
//                    }
//                }
//            }
//        }).start();
//        while (true){
//            Request request = new Request("<priority>fast</priority><encryption>none</encryption><data>small</data>");
//            controller.handleRequest(request);
//            System.out.println(local.getLoad());
//            try {
//                Thread.sleep(2000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        LocalRequestLogger logger = new LocalRequestLogger();
        Request request = new Request("<priority>fast</priority><encryption>none</encryption><data>small</data>");
        logger.log(request);
    }
}
