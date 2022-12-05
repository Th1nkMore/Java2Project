package DataLoader;

import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DataLoader {
    static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DataLoader.class.getName());
    public static void main(String[] args) throws IOException, ParseException, SQLException {

        // Preparation: Read Config
        String configFilePath = "src/main/resources/config.properties";
        FileInputStream propsInput = new FileInputStream(configFilePath);
        Properties prop = new Properties();
        prop.load(propsInput);
        String DB_User = prop.getProperty("DB_USER");
        String DB_Password = prop.getProperty("DB_PASSWORD");
        String DB_URL = prop.getProperty("DB_URL");

        // Ready to connect database
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager
                    .getConnection(DB_URL,
                            DB_User, DB_Password);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        logger.info("Connect to database..");

        // Prepare repo
        repo repo = new repo("yolov5", "ultralytics");

        // Insert Developer Information
        insert_developerInfo(conn, repo);

        // Insert Issue Information
        insert_issueInfo(conn, repo);

        // Insert Release Information
        insert_releaseInfo(conn, repo);

        // Insert Commit Information
        insert_commitInfo(conn, repo);
    }

    private static void insert_developerInfo(@NotNull Connection conn, repo repo) throws SQLException, IOException, ParseException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"Developer\" VALUES (Default,?,?,?)");
        repo.get_developerInfo();
        conn.setAutoCommit(false);
        repo.contributors.forEach(e -> {
            try {
                stmt.setString(1, e.name);
                stmt.setInt(2, e.n_contributions);
                stmt.setString(3, repo.name);
                stmt.addBatch();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

    private static void insert_issueInfo(@NotNull Connection conn, repo repo) throws SQLException, IOException, ParseException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"Issue\" VALUES (Default,?,?,?,?,?,?)");
        repo.get_issueInfo();
        conn.setAutoCommit(false);
        repo.issues.forEach(e -> {
            try {
                stmt.setString(1, e.title);
                stmt.setString(2, e.description);
                stmt.setTimestamp(3, e.created_at);
                stmt.setTimestamp(4, e.closed_at);
                stmt.setString(5, e.state);
                stmt.setString(6, repo.name);
                stmt.addBatch();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }

    private static void insert_releaseInfo(@NotNull Connection conn, repo repo) throws SQLException, IOException, ParseException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"Release\" VALUES (Default,?,?,?)");
        repo.get_releaseInfo();
        conn.setAutoCommit(false);
        repo.releases.forEach(e -> {
            try {
                stmt.setTimestamp(1, e.published_at);
                stmt.setString(2, repo.name);
                stmt.setString(3, e.tag_name);
                stmt.addBatch();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }
    private static void insert_commitInfo(@NotNull Connection conn, repo repo) throws SQLException, IOException, ParseException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO \"Commit\" VALUES (Default,?,?)");
        repo.get_commitInfo();
        conn.setAutoCommit(false);
        repo.commits.forEach(e -> {
            try {
                stmt.setTimestamp(1, e);
                stmt.setString(2, repo.name);
                stmt.addBatch();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        stmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
    }
}