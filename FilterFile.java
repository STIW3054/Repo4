package RealTime;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author
 */
public class FilterFile {

    public static List <File> filter_files_by_type (String Filetype,File Path, List <File> FileList){
        //determined weather the file exist
            if (!Path.exists()){
                System.out.println("The path " + Path + " not existing!");
            }else if (Path.isFile()){
                    //If the current fir is a file, then determine what file
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
                System.out.println("The path " + Path + " not existing!");
        }else if(Path.isFile()){
            if(Path.getName().lastIndexOf(".")!=0 && Path.getName().lastIndexOf(".")!=(-1)){
                String filename = Path.getName();
                if(filename.substring(0,filename.lastIndexOf(".")).equals(FileName)&&!filename.substring(filename.lastIndexOf(".")+1).equals("java")){
                    System.out.println(Path.toPath());
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

    public static void main(String[] args) {
        List <File> FileList = new ArrayList();
        File Path = new File("/Users/Aman/Desktop/Repos/240448");
        FileList = FilterFile.filter_files_by_type("java", Path, FileList);
        System.out.println(FileList.toString());
    }
//    public static List <File> getMain(List <File> FileList){
//
//        File [] FileArray = new File [FileList.size()];
//        FileList.toArray(FileArray);
//        List <File> MainFile = new <File> ArrayList();
//        for (File file : FileArray){
//            String fileName = file.getName();
//            int removeType = fileName.lastIndexOf(".");
//            if (removeType > 0) {
//                fileName = fileName.substring(0, removeType);
//            }
//            CompileFile f = new CompileFile();
//            String packageName = file.getParentFile().getName();
//
//            Method mainMethod;
//            try{
//                Class name;
//                name = Class.forName(fileName);
//                if(!packageName.equals("")){
//                    packageName = packageName.replace("/",".");
//                    name = Class.forName(packageName+"."+fileName);
//                }else {
//                    name = Class.forName(fileName);
//                }
//                mainMethod = name.getMethod("main", new Class[]{String[].class});
//                if(mainMethod != null){
//                    MainFile.add(file);
//                }
//            }catch(ClassNotFoundException | NoSuchMethodException | SecurityException e){
//                System.out.println(e);
//            }
//        }
//        return MainFile;
//    }
}
