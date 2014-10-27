package Requests;

import java.util.Random;

/**
 * Created by koencertyn on 25/10/14.
 */
public class Request {

    private String value;

    private int estimatedDuration;

    public Request(String value){
        setValue(value);
        Random rand = new Random();
        int nb = rand.nextInt(100);
        setEstimatedDuration(nb);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getEstimatedCalculationTime(){
        return getEstimatedDuration();
    }

    public int getEstimatedDataSpace(){
        return 1;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}
