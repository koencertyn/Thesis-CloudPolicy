package Policies;

import Policies.Actions.Action;

import java.util.List;

/**
 * Created by koencertyn on 20/10/14.
 */
public abstract class CloudPolicy {

    private List<Policy> policies;
    public abstract Action getActions();
}
