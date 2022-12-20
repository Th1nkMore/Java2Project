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

/**
 * The type Repo.
 */
public class repo {
    /**
     * The Name.
     */
    public String name;
    /**
     * The Owner.
     */
    public String owner;
    /**
     * The N contributors.
     */
    public int n_contributors;
    /**
     * The Contributors.
     */
    public ArrayList<Contributor> contributors = new ArrayList<>();
    /**
     * The Most active contributor.
     */
    public Contributor most_active_contributor;
    /**
     * The N open issues.
     */
    public int n_open_issues;
    /**
     * The N closed issues.
     */
    public int n_closed_issues;
    /**
     * The Issues.
     */
    public ArrayList<Issue> issues = new ArrayList<>();
    // todo: issue的平均解决时间交给数据库方面计算

    /**
     * The N releases.
     */
    public int n_releases;
    /**
     * The Releases.
     */
    public ArrayList<Release> releases = new ArrayList<>();
    /**
     * The N commits.
     */
    public int n_commits;
    /**
     * The Commits.
     */
    public ArrayList<Timestamp> commits = new ArrayList<>();
    // todo: release之间的commits数交给数据库方面计算


    /**
     * Instantiates a new Repo.
     *
     * @param n the n
     * @param o the o
     */
    public repo(String n, String o) {
        name = n;
        owner = o;
    }

    /**
     * The type Contributor.
     */
    public static class Contributor {
        /**
         * The Name.
         */
        String name;
        /**
         * The N contributions.
         */
        int n_contributions;

        /**
         * Instantiates a new Contributor.
         *
         * @param n  the n
         * @param nc the nc
         */
        public Contributor(String n, int nc) {
            name = n;
            n_contributions = nc;
        }
    }


    /**
     * Gets developer info.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    public void get_developerInfo() throws IOException, ParseException {
        for (int page = 1; true; page++) {
            String url_ = String.format("https://api.github.com/repos/%s/%s/contributors?anon=true&per_page=100&page=%d",
                    owner, name, page);
            String content = getContent(url_);
            JSONArray arr = (JSONArray) (new JSONParser().parse(content));
            if (arr.size() == 0)
                break;
            else
                n_contributors += arr.size();

            for (Object a : arr) {
                JSONObject obj = (JSONObject) a;
                String name_contributor = (String) obj.get("login");
                int n_contributions = Integer.parseInt(obj.get("contributions").toString());

                contributors.add(new Contributor(name_contributor, n_contributions));
            }
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

    /**
     * The type Issue.
     */
    public static class Issue {
        /**
         * The Title.
         */
        String title;
        /**
         * The State.
         */
        String state;
        /**
         * The Description.
         */
        String description;
        /**
         * The Created at.
         */
        Timestamp created_at;
        /**
         * The Closed at.
         */
        Timestamp closed_at;

        /**
         * Instantiates a new Issue.
         *
         * @param title       the title
         * @param state       the state
         * @param description the description
         * @param created_at  the created at
         * @param closed_at   the closed at
         */
        public Issue(String title, String state, String description, Timestamp created_at, Timestamp closed_at) {
            this.title = title;
            this.state = state;
            this.description = description;
            this.created_at = created_at;
            this.closed_at = closed_at;
        }

        /**
         * Gets title.
         *
         * @return the title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets title.
         *
         * @param title the title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Gets state.
         *
         * @return the state
         */
        public String getState() {
            return state;
        }

        /**
         * Sets state.
         *
         * @param state the state
         */
        public void setState(String state) {
            this.state = state;
        }

        /**
         * Gets description.
         *
         * @return the description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets description.
         *
         * @param description the description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * Gets created at.
         *
         * @return the created at
         */
        public Timestamp getCreated_at() {
            return created_at;
        }

        /**
         * Sets created at.
         *
         * @param created_at the created at
         */
        public void setCreated_at(Timestamp created_at) {
            this.created_at = created_at;
        }

        /**
         * Gets closed at.
         *
         * @return the closed at
         */
        public Timestamp getClosed_at() {
            return closed_at;
        }

        /**
         * Sets closed at.
         *
         * @param closed_at the closed at
         */
        public void setClosed_at(Timestamp closed_at) {
            this.closed_at = closed_at;
        }
    }

    /**
     * Gets issue info.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    public void get_issueInfo() throws IOException, ParseException {
        for (int page = 1; true; page++) {
            String url_ = String.format("https://api.github.com/repos/%s/%s/issues?state=all&per_page=100&page=%d",
                    owner, name, page);
            String content = getContent(url_);
            JSONArray arr = (JSONArray) (new JSONParser().parse(content));
            if (arr.size() == 0)
                break;

            for (Object a : arr) {
                JSONObject obj = (JSONObject) a;
                if (obj.get("pull_request") != null)
                    continue;
                String title = (String) obj.get("title");
                String state = (String) obj.get("state");
                if (Objects.equals(state, "open"))
                    n_open_issues++;
                else
                    n_closed_issues++;

                String description = (String) obj.get("body");
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
    }

    /**
     * The type Release.
     */
    public static class Release {
        /**
         * The Tag name.
         */
        String tag_name;
        /**
         * The Published at.
         */
        Timestamp published_at;

        /**
         * Instantiates a new Release.
         *
         * @param tn the tn
         * @param pa the pa
         */
        public Release(String tn, Timestamp pa) {
            tag_name = tn;
            published_at = pa;
        }
    }

    /**
     * Gets release info.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    public void get_releaseInfo() throws IOException, ParseException {
        for (int page = 1; true; page++) {
            String url_ = String.format("https://api.github.com/repos/%s/%s/releases?per_page=100&page=%d", owner, name, page);
            String content = getContent(url_);
            JSONArray arr = (JSONArray) (new JSONParser().parse(content));
            if (arr.size() == 0)
                break;
            else
                n_releases += arr.size();

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
    }

    /**
     * Gets commit info.
     *
     * @throws IOException    the io exception
     * @throws ParseException the parse exception
     */
    public void get_commitInfo() throws IOException, ParseException {
        for (int page = 1; true; page++) {
            String url_ = String.format("https://api.github.com/repos/%s/%s/commits?per_page=100&page=%d", owner, name, page);
            String content = getContent(url_);
            JSONArray arr = (JSONArray) (new JSONParser().parse(content));
            if (arr.size() == 0)
                break;
            else
                n_commits += arr.size();

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
    }

    // TODO Helper methods
    private String getContent(String url_) throws IOException {
        URL url = new URL(url_);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer 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");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine();
    }

    /**
     * Display contributors.
     */
// TODO Display methods
    public void display_contributors() {
        System.out.println("Number of contributors: " + n_contributors);
        for (Contributor c : contributors) {
            System.out.println(c.name + " " + c.n_contributions);
        }
        System.out.println("Most active contributor: " + most_active_contributor.name);
    }

    /**
     * Display issues.
     */
    public void display_issues() {
        System.out.println("Number of open issues: " + n_open_issues);
        System.out.println("Number of closed issues: " + n_closed_issues);
        for (Issue issue : issues) {
            System.out.println(issue.title + " " + issue.state);
            //System.out.println(issue.created_at + " " + issue.closed_at + "\n");
            //System.out.println(issue.description);
        }
    }

    /**
     * Display releases.
     */
    public void display_releases() {
        System.out.println("Number of releases: " + n_releases);
        for (Release release : releases) {
            System.out.println(release.tag_name + "  " + release.published_at);
        }
    }

    /**
     * Display commits.
     */
    public void display_commits() {
        System.out.println("Number of commits: " + n_commits);
//        for (Timestamp timestamp : commits) {
//            System.out.println(timestamp);
//        }
    }

}