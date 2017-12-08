
package com.uum.rt_project;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileTest {
    public static void main(String[] args) {
        Scanner scan = new Scanner (System.in);
        String separator = File.separator;
       
        //
        System.out.println("Please enter location that you want to download files to:");
        String answer = scan.nextLine();
        String directory = answer + separator + "/log_file" ;
        File file = new File (directory);
        file.mkdir() ; 
     
        String fileName = "240102.log";
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
        
    }
 
}

