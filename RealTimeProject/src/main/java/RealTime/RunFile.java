package RealTime;

//run successfully compiled java and maven files (filter .class file)

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunFile {
    static class Runner implements Callable<String> {

        private final String Class;
        private final File Path;
                
        public Runner(String Class,File Path) {
            this.Class = Class;
            this.Path = Path;
        }

        @Override
        public String call() throws Exception {
            System.out.println("\n Now was running class " + Class + " :\n");
            ArrayList <String> Output = ExecuteTerminal.execute("cd " + Path.toString() + " && java -cp ." + LogFile.Se + "target" + LogFile.Se + "classes " + Class);
            for(String output:Output){
                LogFile.CreateOutput(output,Path.getParentFile().toString(),Path.getName());
            }
            return "Success";
        }
    }
    public static synchronized List <String> sort(File Path,List <File> JavaFiles){
        List <String> PackageClassList = new <String> ArrayList();
        List <String> FileNameList = new <String> ArrayList();
        List <File> ClassesFileList = new <String> ArrayList();
        if(JavaFiles.size() > 0){
            for(File JavaFile : JavaFiles){
                FileNameList.add(JavaFile.getName().substring(0, JavaFile.getName().lastIndexOf(".java")));
            }
            File ClassPath = new File(Path.toString() + LogFile.Se + "target" + LogFile.Se + "classes");
            for(String FileName : FileNameList){
                ClassesFileList = FilterFile.filter_files_by_name(FileName, ClassPath,ClassesFileList);
            }
            if(ClassesFileList.size()>0){
                for(File ClassesFile : ClassesFileList){
                    PackageClassList.add((ClassesFile.toString().substring((Path.toString() + LogFile.Se + "target" + LogFile.Se + "classes" + LogFile.Se + "").length(),ClassesFile.toString().lastIndexOf("."))).replaceAll(LogFile.Se, "."));
                }
            }
        }
        return PackageClassList;
    }
    
    public static synchronized Map run(File Path,List <File> FileList) throws IOException{
        List <String> Classes = sort(Path,FileList);
        ExecutorService FixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Map<Future<String>,String> futures = new HashMap<>();
        for(String Class : Classes){
            Future<String> future = FixedThreadPool.submit(new RunFile.Runner(Class, Path));
            futures.put(future,Class);
        }
        for(Future<String> fu:futures.keySet()){
            try {
                fu.get(30, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException ex) {
                fu.cancel(true);
                System.out.println("Already cancel!");
                Logger.getLogger(RunFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                fu.cancel(true);
                System.out.println("Already cancel!");
            }
        }
        FixedThreadPool.shutdown();
        return futures;    
    }
}
