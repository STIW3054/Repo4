package RealTime;

/**
 *
 * @author Group4
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogFile {

    public static String Se = File.separator;

    public static synchronized void CreateLog(String LogDetail, String Path, String Matric) throws IOException{
        Date LogTime = new Date();
        // create folder in harddisk
        File ReposFolder = new File(Path);
        File ResultPath = new File(ReposFolder.getParentFile().toString() + Se + "STIW3054-Result");
        ResultPath.mkdir();
        File LogFolder = new File(ResultPath.toString() + Se + "Log File");
        LogFolder.mkdir();

        String LogFileName = Matric + ".log";
        
        File LogFile = new File(LogFolder, LogFileName);
        try {
            // create new Log file
            LogFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed to create log file...");
            e.printStackTrace();
        }

//        String data = "Repo from " + Matric + " is run out of time. Termination time: " + LogTime;
        try {
            try (FileWriter fw = new FileWriter(LogFile.toString(),true)) {
                fw.write(LogTime + ":\t" + LogDetail + "\n");
                System.out.println(Matric + ": Log file already created to " + LogFolder.toString());
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public static synchronized void CreateError(String ErrorDetail, String Path, String Matric) throws IOException{
        Date ErrorTime = new Date();
        // create folder in harddisk
        File ReposFolder = new File(Path);
        File ResultPath = new File(ReposFolder.getParentFile().toString() + Se + "STIW3054-Result");
        ResultPath.mkdir();
        File ErrorFolder = new File(ResultPath.toString() + Se + "Error File");
        ErrorFolder.mkdir();

        String ErrorFileName = Matric + ".err";
        
        File ErrorFile = new File(ErrorFolder, ErrorFileName);
        try {
            ErrorFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed to create Error Log file...");
            e.printStackTrace();
        }

//        String data = "Repo from " + Matric + " is run out of time. Termination time: " + ErrorTime;
        try {
            try (FileWriter fw = new FileWriter(ErrorFile.toString(),true)) {
                fw.write(ErrorTime + ":\t" + ErrorDetail + "\n");
                System.out.println("Compiling... " + Matric + ": Error file already created to " + ErrorFolder.toString());
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public static synchronized void CreateOutput(String OutputDetail, String Path, String Matric) throws IOException{
        Date OutputTime = new Date();
        // create folder in harddisk
        File ReposFolder = new File(Path);
        File ResultPath = new File(ReposFolder.getParentFile().toString() + Se + "STIW3054-Result");
        ResultPath.mkdir();
        File OutputFolder = new File(ResultPath.toString() + Se + "Output File");
        OutputFolder.mkdir();

        String OutputFileName = Matric + ".out";
        
        File OutputFile = new File(OutputFolder, OutputFileName);
        try {
            OutputFile.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed to create Output Log file...");
            e.printStackTrace();
        }

//        String data = "Repo from " + Matric + " is run out of time. Termination time: " + OutputTime;
        try {
            try (FileWriter fw = new FileWriter(OutputFile.toString(),true)) {
                fw.write(OutputTime + ":\t" + OutputDetail + "\n");
                System.out.println("Running... " + Matric + ": Output file already created to " + OutputFolder.toString());
                fw.close();;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
