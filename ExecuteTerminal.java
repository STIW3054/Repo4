package RealTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ExecuteTerminal {

    public static ArrayList<String> execute(String commandString) {
        String line = null;
        ArrayList <String> Output = new <String> ArrayList();
        try {
            ProcessBuilder builder;
            if(System.getProperties().getProperty("os.name").subSequence(0, 3).equals("Mac")){
                builder = new ProcessBuilder("/bin/sh","-c",commandString);
            }else{
                builder = new ProcessBuilder("cmd","/c",commandString);
            }
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
//                System.out.println(line);
                Output.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Output;
    }
}