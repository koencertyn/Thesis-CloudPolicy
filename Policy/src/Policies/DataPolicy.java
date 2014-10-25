package Policies;

/**
 * Created by koencertyn on 20/10/14.
 */
public class DataPolicy extends Policy {

    public DataPolicy(Value value){
        setValue(value);
    }

    @Override
    public CloudPolicyType getPolicyType() {
        return CloudPolicyType.DATAMODEL;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    private DataPolicy.Value value;

    public enum Value{
        SMALL, MEDIUM, LARGE
    }
}
