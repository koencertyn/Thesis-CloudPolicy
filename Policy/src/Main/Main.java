package Main;

import Interpreters.RequestInterpreter;

/**
 * Created by koencertyn on 20/10/14.
 */
public class Main {
    public static void main(String args[]){
        String str = "<priority> fast </priority><data> qsdf </data><encryption> required </encryption>";
        RequestInterpreter a = new RequestInterpreter();
        System.out.println(a.getAttributes(str));
    }
}
