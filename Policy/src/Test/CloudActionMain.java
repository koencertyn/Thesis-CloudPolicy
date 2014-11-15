package Test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by koencertyn on 15/11/14.
 */
public class CloudActionMain {
    public static void main(String args[]){
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "/Users/koencertyn/thesis/Thesis-CloudPolicy/Policy/scripts/herokuDeploy.sh");
            Process p = pb.start();     // Start the process.
            InputStream is = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null)
                System.out.println(line);
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        }
                        // Wait for the process to finish.
            System.out.println("Script executed successfully");
    }
}