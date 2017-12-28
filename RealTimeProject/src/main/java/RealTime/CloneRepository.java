
package RealTime;

//clone repository using Executor
//get matricNo of the repo
//store the termination time 

import java.io.FileNotFoundException;
import java.io.FileReader;
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
            return git;
        }
    }

    public static List <String> CloneRep(String Path, String Foldername) throws InterruptedException, IOException {
        
        ExecutorService FixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        
        JSONParser parser = new JSONParser();
        Object obj;
        Map<Future<String>,String> futures = new HashMap<>();
        String repo = null;
        
        List <String> repos = new ArrayList();
        
        try {
            obj = parser.parse(new FileReader("githubrepo.json"));
            JSONObject jsonObject = (JSONObject) obj;            
            for (int i = 9; i < 21; i++) {
                String id = Integer.toString(i);
                repo = (String) jsonObject.get(id);
                Future<String> future = FixedThreadPool.submit(new Runner(repo,Path));
                futures.put(future,repo);
                repos.add(repo);
            }
        } catch (FileNotFoundException ex) {
                Logger.getLogger(CloneRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException ex) {
                Logger.getLogger(CloneRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(Future<String> fu:futures.keySet()){
            try {
                fu.get(2, TimeUnit.MINUTES);
            }catch (TimeoutException ex) {
                System.out.println("\nRepo \""+futures.get(fu)+"\" download timed out!\n");
                fu.cancel(true);
                String matric = futures.get(fu);
                String matricL = matric.substring(matric.lastIndexOf(".git")-6, matric.lastIndexOf(".git"));
                LogFile.CreateLog("The repository of " + matricL + " downloading time out!",Path, matricL);
            } catch (ExecutionException ex) {
                Logger.getLogger(CloneRepository.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        FixedThreadPool.shutdown();
        return repos;
    }
}

