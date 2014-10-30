package Test;

import Controllers.RequestController;
import Interpreters.CloudPolicyInterpreter;
import Interpreters.LocalConditionInterpreter;
import Interpreters.RequestInterpreter;
import Policies.CloudPolicy;
import Requests.Request;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Created by koencertyn on 20/10/14.
 */
public class Main {
    public static void main(String args[]){
        final LocalConfig local = new LocalConfig(70);
        LocalConditionInterpreter localInt = new LocalConditionInterpreter();
        RequestInterpreter requestInterpreter = new RequestInterpreter();
        RequestController controller = new RequestController(localInt, requestInterpreter, local);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Second thread started");
                while(true) {
                    try {
                        local.recalculateJobs();
                        Thread.sleep(2000);  // wait two seconds
                    } catch (Exception e) {
                    }
                }
            }
        }).start();
        while (true){
            System.out.println("__________________________________________________");
            Request request = createRandomRequest();
            controller.handleRequest(request);
            System.out.println(local.getLoad());
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Request createRandomRequest(){
        String[] standardProperties = {"priority","encryption","data"};
        String[] standardValues = {"basic","average","extended"};
        String[] priorityValues = {"low","medium","high"};
        String string = "";
        for(int i = 0 ; i < 3 ; i++){
            if(standardProperties[i].equals("data")){
                Random rand = new Random();
                int value = rand.nextInt(3);
                string = string+("<"+standardProperties[i]+">"+standardValues[value]+"</"+standardProperties[i]+">");
            } else if (standardProperties[i].equals("priority")){
                Random rand = new Random();
                int value = rand.nextInt(3);
                string = string+("<"+standardProperties[i]+">"+priorityValues[value]+"</"+standardProperties[i]+">");
            } else{
                Random rand = new Random();
                int value = rand.nextInt(2);
                if(value == 1){
                    string = string+("<"+standardProperties[i]+">yes</"+standardProperties[i]+">");
                } else {
                    string = string+("<"+standardProperties[i]+">no</"+standardProperties[i]+">");
                }
            }
        }
        Request request = new Request(string);
        return request;
    }
}
