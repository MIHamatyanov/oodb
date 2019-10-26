package lab4;

import com.google.gson.Gson;
import lab1.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Lab4Service {
    public static boolean save(List<Bank> banks) {
        if (banks != null && banks.size() > 0) {
            Gson gson = new Gson();
            Connection connection = DBConnection.getConnection();

            try {
                PreparedStatement statement = connection.prepareStatement("insert into banks (content, contentb) values (cast(? as json), cast(? as jsonb))");
                for (Bank bank : banks) {
                    String bankAsJson = gson.toJson(bank);

                    statement.setString(1, bankAsJson);
                    statement.setString(2, bankAsJson);

                    statement.addBatch();
                }
                int[] count = statement.executeBatch();

                System.out.println(Arrays.stream(count).sum() + " records added!");

                statement.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    public static List<Bank> loadJson() {
        return load("select content from banks");
    }

    public static List<Bank> loadJsonb() {
        return load("select contentb from banks");
    }

    public static Bank loadByName(String name) {
        List<Bank> banks = load("select contentb from banks where contentb->>'name'='" + name + "'");

        if (banks != null) {
            return banks.get(0);
        }

        return null;
    }

    private static List<Bank> load(String query) {
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            long start = System.nanoTime();
            ResultSet rs = statement.executeQuery();
            long finish = System.nanoTime();

            System.out.printf(query.contains("contentb") ? "Jsonb: %d\n" : "Json: %d\n", (finish - start));

            List<Bank> banks = new ArrayList<>();
            while (rs.next()) {
                Bank bank = new Gson().fromJson(rs.getString(1), Bank.class);
                banks.add(bank);
            }

            return banks;
        } catch (SQLException e) {
            return null;
        }
    }
}
