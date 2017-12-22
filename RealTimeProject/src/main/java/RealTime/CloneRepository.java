
package RealTime;

//clone repository using Executor
//get matricNo of the repo
//store the termination time 

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Aman
 */
public class CloneRepository {
    static class Runner implements Callable<String> {

        private final String git;
        private final String folderpath;
                
        public Runner(String git,String folderpath) {
            this.git = git;
            this.folderpath = folderpath;
        }

        @Override
        public String call() throws Exception {
            JGitClone.clone(git,folderpath);
//            Set<File> FileSet = new HashSet();
//            File Path = new File (folderpath + git.substring(git.lastIndexOf(".git")-6,git.lastIndexOf(".git")));
//            
//            很重要
//            FileSet = FindJavaFile.getJavaFiles(Path,FileSet);
            return git;
        }
    }

    public static void CloneRep() throws InterruptedException {
        JFileChooser fc = new JFileChooser(""); 
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fc.showOpenDialog(null);  
        String folderpath = null;
        String logFileFolderPath = null;
        
        if (returnValue == JFileChooser.APPROVE_OPTION){  
            folderpath = fc.getSelectedFile().getPath();
            logFileFolderPath = folderpath;
        } else{
            System.out.println("Failed");
        }
        String foldername = JOptionPane.showInputDialog("Please input folder name you want create to store your files:");        
        folderpath = folderpath + "/" + foldername + "/";
        
        JSONParser parser = new JSONParser();
        Object obj;
        ExecutorService FixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Map<Future<String>,String> futures = new HashMap<>();
        try {
            obj = parser.parse(new FileReader("githubrepo.json"));
            JSONObject jsonObject = (JSONObject) obj;            
            for (int i = 1; i < 34; i++) {
                String id = Integer.toString(i);
                String repo = (String) jsonObject.get(id);
                Future<String> future = FixedThreadPool.submit(new Runner(repo,folderpath));
                futures.put(future,repo);
            }
        } catch (FileNotFoundException ex) {
                Logger.getLogger(CloneRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
                Logger.getLogger(CloneRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Future<String> fu:futures.keySet()){
            try {
                fu.get(1, TimeUnit.MINUTES);
            }catch (TimeoutException ex) {
                System.out.println("\nRepo \""+futures.get(fu)+"\" download timed out!\n");
                fu.cancel(true);
                String matric = futures.get(fu);
                String matricL = matric.substring(matric.lastIndexOf(".git")-6, matric.lastIndexOf(".git"));
                LogFile.createLogFile(matricL, logFileFolderPath);
            } catch (ExecutionException ex) {
                Logger.getLogger(CloneRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FixedThreadPool.shutdown();
    }
}

