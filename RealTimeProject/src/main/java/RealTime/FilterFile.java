package RealTime;

import java.io.File;
import java.util.List;

/**
 *
 * @author Group4
 */
public class FilterFile {
    
    public static List <File> filter_files_by_type (String Filetype,File Path, List <File> FileList){        
        //判断文件是否存在  
            if (!Path.exists()){  
//                System.out.println("The path " + Path + " not existing!");
            }else if (Path.isFile()){  
                    //如果当前fir是文件，则判断是什么文件  
                    String firName = Path.getName();//获取文件名                        
                    //System.err.println(firName.hashCode());  获取文件后缀名  
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
