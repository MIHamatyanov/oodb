package lab4;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/oodb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12345";

    private static DBConnection instance;

    private Connection connection;

    private DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (instance == null) {
            instance = new DBConnection();
        }

        return instance.connection;
    }
}
