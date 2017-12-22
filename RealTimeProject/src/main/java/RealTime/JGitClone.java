package RealTime;

//clone using JGit

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import java.io.File;

/**
 *
 * @author Aman
 */
public class JGitClone {
    private String localPath, remotePath, branch;
    private Repository localRepo;
    private Git git;

    public static void clone(String repo,String folder){
        File path = new File(folder + repo.substring(repo.lastIndexOf(".git")-6,repo.lastIndexOf(".git")));
        gitClone(repo,path);
    }

    public static void gitClone(String remoteUrl, File repoDir) {
        try {
            Git git = Git.cloneRepository().setURI(remoteUrl).setDirectory(repoDir).call();
            System.out.println("Cloning from " + remoteUrl + " to " + git.getRepository());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String download(String appName, String codeUrl) {
        try {
            //代码拷贝目录
            localPath = System.getProperty("user.home") + "/" + appName;
            localRepo = new FileRepository(localPath + "/.git");
            git = new Git(localRepo);
            String[] gitCodeUrl = codeUrl.split("::");
            if (gitCodeUrl.length != 2) {
                return "git url path please follow: git@gitlab.[host].com:esigngroup/lhotse_hsftest.git::[branch]";
            }
            remotePath = gitCodeUrl[0];
            branch = gitCodeUrl[1];
            File localPathFile = new File(localPath);
            // 如果没有该代码目录,就执行git clone
            if (!localPathFile.exists()) {
                gitClone(remotePath, branch, localPath);
            } else { // 如果有代码,git pull
                gitPull(branch);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return localPath;
    }
    private void gitClone(String remotePath, String branch, String copyPath) {
        try {
            Git.cloneRepository().setURI(remotePath).setBranch(branch).setDirectory(new File(copyPath)).call();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void gitPull(String branch) {
        try {
            git.pull().setRemoteBranchName(branch).call();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
} 