package Test;

import Requests.Request;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by koencertyn on 26/10/14.
 */
public class LocalConfig {

    private static int load;
    private ArrayList<Request> jobList;

    public LocalConfig(int iniLoad){
        this.load = iniLoad;
        jobList = new ArrayList<Request>();
    }

    public void addJob(Request request){
        jobList.add(request);
        setLoad(true);
    }

    public void removeJob(Request request){
        jobList.remove(request);
        setLoad(false);
    }

    public void recalculateJobs(){
        for(Request request : jobList){
            if(request.getEstimatedCalculationTime() < 1)
                removeJob(request);
            else
                request.setEstimatedDuration(request.getEstimatedCalculationTime() - 5);
        }
    }

    public static int getLoad(){
        return load;
    }

    private void setLoad(boolean add){
        Random rand = new Random();
        int value = rand.nextInt(20);
        if(add){
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
