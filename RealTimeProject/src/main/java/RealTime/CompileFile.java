package RealTime;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Aman
 */
public class CompileFile {
    
    public static Map <String, Integer> sort(List <File> FileList) {
        
        Map <String, Integer> FolderMap = new <String, Integer> HashMap();
        for(File F:FileList){
            String Folder = F.toString().substring(0,F.toString().lastIndexOf(F.getName()));
            if (FolderMap.containsKey(Folder)) {
                int value = FolderMap.get(Folder) + 1;
                FolderMap.put(Folder, value);
            }else{
                FolderMap.put(Folder,1);
            }
        }
        return FolderMap; 
    }
    public static List <File> compile(File Path,List <File> FileList) throws IOException{
        Map <String, Integer> CompilePath = sort(FileList);
        
        if(!FilterFile.isMaven(Path)){
            for (Map.Entry<String, Integer> entry : CompilePath.entrySet()) {  
                ArrayList <String> Return = ExecuteTerminal.execute("cd " + Path.toString() +" && mkdir target && mkdir target" + LogFile.Se + "classes");
                ArrayList <String> Return2 = ExecuteTerminal.execute("cd " + Path.toString() +" && javac -d ." + LogFile.Se + "target" + LogFile.Se + "classes " + entry.getKey() + "*.java");
                for(String output2 : Return2){
                    LogFile.CreateError(output2, Path.getParentFile().toString(), Path.getName());
                }
            }
        } else {
            ExecuteTerminal.execute("cd " + Path.toString() +" && mvn compile");
        }
        return FileList;
    }  
    
}
