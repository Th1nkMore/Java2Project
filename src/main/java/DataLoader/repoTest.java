package DataLoader;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public class repoTest {
    public static void main(String[] args) throws IOException, ParseException {
        // Sample 1: https://api.github.com/repos/shiqiyu/cpp/...
        // Repo repo = new Repo("CPP", "ShiqiYu");
        // Sample 2: https://api.github.com/repos/ultralytics/yolov5/...
        repo repo = new repo("yolov5", "ultralytics");

//        // TODO 1 developers
//        System.out.println("1 Developer Information");
//        repo.get_developerInfo();
//        repo.display_contributors();
//
//        // TODO 2 issues
//        System.out.println("\n2 Issue Information");
//        repo.get_issueInfo();
//        repo.display_issues();
//
//        // TODO 3 releases
//        System.out.println("\n3 Release Information");
//        repo.get_releaseInfo();
//        repo.display_releases();
//
//        // TODO 4 commits
//        System.out.println("\n4 Commit Information");
//        repo.get_commitInfo();
//        repo.display_commits();
        String s = "hel1456lo56wor%^ld";
        s=s.replaceAll("[^a-zA-Z]","");
        System.out.println(s);

    }
}