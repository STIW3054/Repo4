package RealTime;

//for main process

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MainProcess {
    
    static class Runner implements Callable<String> {

        private List <File> FileList;
        private final String ReposPath;
        private final String git;
                
        public Runner(List <File> FileList,String ReposPath,String git) {
            this.FileList = FileList;
            this.ReposPath = ReposPath;
            this.git = git;
        }

        @Override
        public String call() throws Exception {
            FileList.clear();
            File Path = new File (ReposPath + git.substring(git.lastIndexOf(".git")-6,git.lastIndexOf(".git")));
            FileList = FilterFile.filter_files_by_type("java", Path,FileList);
            if(FileList.size()>0){
                List ReturnFileList = CompileFile.compile(Path,FileList);
                
                RunFile.run(Path,ReturnFileList).wait();
            }
            return git;
        }
    }
    
    public static void main(String[] args) throws InterruptedException, IOException, Exception {
        
        ExecutorService FixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        Map<Future<String>,String> futures = new HashMap<>();
        
        JFileChooser fc = new JFileChooser(); 
        fc.setApproveButtonText("Choose");
        fc.setDialogTitle("Please select the path you want to store files");
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fc.showOpenDialog(null);  
        String FolderPath = null;
        String ReposPath = null;
        
        List <File> FileList = new <File> ArrayList();
        
        if (returnValue == JFileChooser.APPROVE_OPTION){  
            FolderPath = fc.getSelectedFile().getPath();
        } else{
            System.out.println("Failed to selecte path!");
        }
        String FolderName = JOptionPane.showInputDialog("Please input folder name you want create to store your files:");        
        ReposPath = FolderPath + LogFile.Se + FolderName + LogFile.Se;
        List <String> gits = CloneRepository.CloneRep(ReposPath,FolderName);
        
        ExcelFile.CreateExcel(ReposPath);
        List <Future> Futures = new ArrayList();
        for(String git:gits){
            Future<String> future = FixedThreadPool.submit(new MainProcess.Runner(FileList,ReposPath,git));
            Futures.add(future);
        }
        
        while(!FixedThreadPool.isTerminated()){
        }
        File SummaryFolder = new File(FolderPath + LogFile.Se + "STIW3054-Result" + LogFile.Se + "Summary");
        PDFfile.writePDF(SummaryFolder.toString(), SummaryFolder.getParentFile().toString() + LogFile.Se + "Log File", "Log");
        PDFfile.writePDF(SummaryFolder.toString(), SummaryFolder.getParentFile().toString() + LogFile.Se + "Error File", "Error");
        PDFfile.writePDF(SummaryFolder.toString(), SummaryFolder.getParentFile().toString() + LogFile.Se + "Output File", "Output");
        FixedThreadPool.shutdownNow();
    }
    
}