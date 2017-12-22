package RealTime;

//create log file and store message (may parse matricNo and termination time from CloneRepository.java)

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//store to Log folder
public class LogFile {
    static String directory, folderpath, fileName;
    
    public static void createLogFile(String matric, String folderpath){
        String separator = File.separator;
        directory = folderpath + separator + "/log file" ;
       
        File file = new File (directory);
        file.mkdir() ; 
     
        fileName = matric + ".log";
        // create folder in harddisk
        File f = new File(directory,fileName);
        if(f.exists()) {
          // file name existed, print exist file information
            System.out.println(f.getAbsolutePath());
            System.out.println(f.getName());
            System.out.println(f.length());
        } else {
          //create new file directory
            f.getParentFile().mkdirs();
            try {
             // create new file
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Can't Create File ...");
                e.printStackTrace();
            }
        }
        String data = "THIS REPO IS RUN OUT OF TIME";
         FileWriter fr = null;
        try {
            fr = new FileWriter(f);
            fr.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
