package Main;

import Interpreters.CloudPolicyInterpreter;
import Interpreters.RequestInterpreter;
import Policies.CloudPolicy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by koencertyn on 20/10/14.
 */
public class Main {
    public static void main(String args[]){
        String str = "<priority> fast </priority><data> qsdf </data><encryption> required </encryption>";
        CloudPolicyInterpreter a = new CloudPolicyInterpreter();
        System.out.println(a.getAttributes());
    }

    public static String readFile(String filename)
    {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
