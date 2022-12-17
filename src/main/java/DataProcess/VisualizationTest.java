package DataProcess;

import DataLoader.DataLoader;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class VisualizationTest {
    static java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DataLoader.class.getName());

    public static void main(String[] args) throws IOException {
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

        // Read Table Commit
        Table commit = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"commit\" ")) {
            try (ResultSet results = stmt.executeQuery()) {
                commit = Table.read().db(results, "commit");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        // Read Table Commit
        Table developer = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM \"developer\" ")) {
            try (ResultSet results = stmt.executeQuery()) {
                developer = Table.read().db(results, "developer");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        assert developer != null;
        Plot.show(
                HorizontalBarPlot.create(
                        "contributions", // plot title
                        developer.sortAscendingOn("contributions").last(20), // table
                        "name", // grouping column name
                        "contributions")); // numeric column name
    }
}