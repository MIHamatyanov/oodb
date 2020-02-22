package lab7.init;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBScan {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/oodb",
                "postgres",
                "12345"
        );
    }

    private static void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTables() {
        List<String> result = new ArrayList<>();
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select table_name from information_schema.tables " +
                            "where table_type = 'BASE TABLE' and table_schema not in ('pg_catalog', 'information_schema')"
            );

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String tableName = rs.getString("table_name");
                result.add(tableName);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return result;
    }

    public List<String> getField(String tableName) {
        List<String> result = new ArrayList<>();
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "select a.attname from pg_catalog.pg_attribute a " +
                            "where a.attrelid = (select c.oid from pg_catalog.pg_class c left join pg_catalog.pg_namespace n on n.oid = c.relnamespace where pg_catalog.pg_table_is_visible(c.oid) and c.relname = ?) " +
                            "and a.attnum > 0 and not a.attisdropped"
            );
            statement.setString(1, tableName);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String field = rs.getString("attname");
                result.add(field);
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return result;
    }
}
