package Policies;

/**
 * Created by koencertyn on 25/10/14.
 */
public abstract class PolicyValue<T> {

    private T value;

    protected PolicyValue(T value) {
        this.value = value;
    }

    public abstract boolean isValidValue(T value);
}
