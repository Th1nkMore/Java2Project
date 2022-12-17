package DataLoader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

public class repo {
    public String name;
    public String owner;
    public int n_contributors;
    public ArrayList<Contributor> contributors = new ArrayList<>();
    public Contributor most_active_contributor;
    public int n_open_issues;
    public int n_closed_issues;
    public ArrayList<Issue> issues = new ArrayList<>();
    // todo: issue的平均解决时间交给数据库方面计算

    public int n_releases;
    public ArrayList<Release> releases = new ArrayList<>();
    public int n_commits;
    public ArrayList<Timestamp> commits = new ArrayList<>();
    // todo: release之间的commits数交给数据库方面计算


    public repo(String n, String o) {
        name = n;
        owner = o;
    }

    public static class Contributor {
        String name;
        int n_contributions;

        public Contributor(String n, int nc) {
            name = n;
            n_contributions = nc;
        }
    }


    public void get_developerInfo() throws IOException, ParseException {
        String url_ = String.format("https://api.github.com/repos/%s/%s/contributors?per_page=100", owner, name);
        String content = getContent(url_);
        JSONArray arr = (JSONArray) (new JSONParser().parse(content));
        n_contributors = arr.size();
        for (Object a : arr) {
            JSONObject obj = (JSONObject) a;
            String name_contributor = (String) obj.get("login");
            int n_contributions = Integer.parseInt(obj.get("contributions").toString());

            contributors.add(new Contributor(name_contributor, n_contributions));
        }

        int maxContributions = 0;
        Contributor mac = null;
        for (Contributor c : contributors) {
            if (c.n_contributions > maxContributions) {
                maxContributions = c.n_contributions;
                mac = c;
            }
        }
        most_active_contributor = mac;
    }

    public static class Issue {
        String title;
        String state;
        String description;
        Timestamp created_at;
        Timestamp closed_at;

        public Issue(String title, String state, String description, Timestamp created_at, Timestamp closed_at) {
            this.title = title;
            this.state = state;
            this.description = description;
            this.created_at = created_at;
            this.closed_at = closed_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Timestamp getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Timestamp created_at) {
            this.created_at = created_at;
        }

        public Timestamp getClosed_at() {
            return closed_at;
        }

        public void setClosed_at(Timestamp closed_at) {
            this.closed_at = closed_at;
        }
    }

    public void get_issueInfo() throws IOException, ParseException {
        String url_ = String.format("https://api.github.com/repos/%s/%s/issues?state=all&per_page=100", owner, name);
        String content = getContent(url_);
        System.out.println(content);
        JSONArray arr = (JSONArray) (new JSONParser().parse(content));
        for (Object a : arr) {
            JSONObject obj = (JSONObject) a;
            String title = (String) obj.get("title");
            String state = (String) obj.get("state");
            if (Objects.equals(state, "open"))
                n_open_issues++;
            else
                n_closed_issues++;

            String description = ((String) obj.get("body"));
//            if (description != null)
//                description = description.length() > 255 ? description.substring(0, 255) : description;

            String created_time = (String) obj.get("created_at");
            created_time = created_time.replace("T", " ");
            created_time = created_time.replace("Z", "");
            Timestamp created_at = Timestamp.valueOf(created_time);

            String closed_time = (String) obj.get("closed_at");
            Timestamp closed_at = null;
            if (closed_time != null) {
                closed_time = closed_time.replace("T", " ");
                closed_time = closed_time.replace("Z", "");
                closed_at = Timestamp.valueOf(closed_time);
            }

            issues.add(new Issue(title, state, description, created_at, closed_at));
        }
    }

    public static class Release {
        String tag_name;
        Timestamp published_at;

        public Release(String tn, Timestamp pa) {
            tag_name = tn;
            published_at = pa;
        }
    }

    public void get_releaseInfo() throws IOException, ParseException {
        String url_ = String.format("https://api.github.com/repos/%s/%s/releases?per_page=100", owner, name);
        String content = getContent(url_);
        JSONArray arr = (JSONArray) (new JSONParser().parse(content));
        n_releases = arr.size();

        for (Object a : arr) {
            JSONObject obj = (JSONObject) a;
            String tag_name = (String) obj.get("tag_name");
            String published_time = (String) obj.get("published_at");
            published_time = published_time.replace("T", " ");
            published_time = published_time.replace("Z", "");
            Timestamp published_at = Timestamp.valueOf(published_time);

            releases.add(new Release(tag_name, published_at));
        }
    }

    public void get_commitInfo() throws IOException, ParseException {
        String url_ = String.format("https://api.github.com/repos/%s/%s/commits?per_page=100", owner, name);
        String content = getContent(url_);
        JSONArray arr = (JSONArray) (new JSONParser().parse(content));
        n_commits = arr.size();

        for (Object a : arr) {
            JSONObject obj = (JSONObject) a;
            JSONObject commitObj = (JSONObject) obj.get("commit");
            JSONObject committerObj = (JSONObject) commitObj.get("committer");
            String commit_date = (String) committerObj.get("date");
            commit_date = commit_date.replace("T", " ");
            commit_date = commit_date.replace("Z", "");
            Timestamp commit_time = Timestamp.valueOf(commit_date);

            commits.add(commit_time);
        }
    }

    // TODO Helper methods
    private String getContent(String url_) throws IOException {
        URL url = new URL(url_);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }

    // TODO Display methods
    public void display_contributors() {
        System.out.println("Number of contributors: " + n_contributors);
        for (Contributor c : contributors) {
            System.out.println(c.name + " " + c.n_contributions);
        }
        System.out.println("Most active contributor: " + most_active_contributor.name);
    }

    public void display_issues() {
        System.out.println("Number of open issues: " + n_open_issues);
        System.out.println("Number of closed issues: " + n_closed_issues);
        for (Issue issue : issues) {
            System.out.println(issue.title + " " + issue.state);
            //System.out.println(issue.created_at + " " + issue.closed_at + "\n");
            //System.out.println(issue.description);
        }
    }

    public void display_releases() {
        System.out.println("Number of releases: " + n_releases);
        for (Release release : releases) {
            System.out.println(release.tag_name + "  " + release.published_at);
        }
    }

    public void display_commits() {
        System.out.println("Number of commits: " + n_commits);
        for (Timestamp timestamp : commits) {
            System.out.println(timestamp);
        }
    }

}