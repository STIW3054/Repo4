package RealTime;

import java.io.File;
import java.util.List;

/**
 *
 * @author Group4
 */

public class FilterFile {

    public static List <File> filter_files_by_type (String Filetype,File Path, List <File> FileList){
        //determine weathe the file exist
            if (!Path.exists()){
//                System.out.println("The path " + Path + " not existing!");
            }else if (Path.isFile()){
                    //If the current fir is a file, then determine what file
                    String firName = Path.getName();//get file name
                    //System.err.println(firName.hashCode());  //get file extension
                    if(firName.substring(firName.lastIndexOf(".")+1) .equals(Filetype)){
                        FileList.add(Path);
                    }
                }else{
                    File [] subfiles = Path.listFiles();
                    for(File sub : subfiles){
                        filter_files_by_type(Filetype,sub,FileList);
                    }
                }

        return FileList;
    }

    public static List <File> filter_files_by_name (String FileName,File Path,List <File> FileList){
        if (!Path.exists()){
//                System.out.println("The path " + Path + " not existing!");
        }else if(Path.isFile()){
            if(Path.getName().lastIndexOf(".")!=0 && Path.getName().lastIndexOf(".")!=(-1)){
                String filename = Path.getName();
                if(filename.substring(0,filename.lastIndexOf(".")).equals(FileName)&&!filename.substring(filename.lastIndexOf(".")+1).equals("java")){
//                    System.out.println(Path.toPath());
                    FileList.add(Path);
                }
            }
        }else{
            File [] subfiles = Path.listFiles();
            for(File sub : subfiles){
                filter_files_by_name(FileName,sub,FileList);
            }
        }
        return FileList;
    }

    public static Boolean isMaven(File Path){
        Boolean r = null;
        File [] subfiles = Path.listFiles();
        for(File sub : subfiles){
            if (sub.isFile()){
                String firName = sub.getName();
                if(firName.equals("pom.xml")){
                    r = true;
                    return r;
                }else{
                    r = false;
                }
            }
        }
        return r;
    }
}
