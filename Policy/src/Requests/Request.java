package Requests;

/**
 * Created by koencertyn on 25/10/14.
 */
public class Request {

    private String value;

    public Request(String value){
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
