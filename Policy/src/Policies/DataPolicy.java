package Policies;

import Policies.Actions.Action;

/**
 * Created by koencertyn on 20/10/14.
 */
public class DataPolicy extends Policy{

    public DataPolicy(){

    }

    @Override
    public Action getActions() {
        return new Action() {
            @Override
            public String getCommand() {
                return "test";
            }
        };
    }
}
