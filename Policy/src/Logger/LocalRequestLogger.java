package Logger;

import Interpreters.RequestInterpreter;
import Requests.Request;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by koencertyn on 28/10/14.
 */
public class LocalRequestLogger {

    public void log(Request request){

        File file = new File("/Users/koencertyn/thesis/Thesis-CloudPolicy/Policy/local_log.log");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            out.println(dateFormat.format(date)+"  : "+request.getValue());
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
