package Test;

import Requests.Request;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by koencertyn on 26/10/14.
 */
public class CloudConfig {
    public CloudConfig(String name, int iniLoad){
        this.load = iniLoad;
        jobList = new ArrayList<Request>();
    }

    private int load;
    private ArrayList<Request> jobList;
    private String cloudName;

    public void addJob(Request request){
        jobList.add(request);
    }

    public void removeJob(){
        jobList.remove(0);
    }

    public int getLoad(){
        return this.load;
    }

    private void setLoad(){
        Random rand = new Random();
        int value = rand.nextInt(50);
        int min = rand.nextInt(1);
        if(min == 1){
            load = load + value;
            if(load > 100)
                load = 100;
        } else {
            load = load - value;
            if(load < 0)
                load = 0;
        }
    }
}
