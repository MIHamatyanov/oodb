package lab5;

import lab5.model.Bank;
import lab5.model.BankAccount;
import lab5.model.Client;
import lab5.model.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Lab5Repository {
    private static final String URL = "jdbc:postgresql://localhost:5432/oodb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12345";

    public Lab5Repository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Bank> findAll() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            List<Bank> result = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement("SELECT" +
                    " id," +
                    " (bank).bank_name," +
                    " (((bank).worker).worker).firstname as worker_firstname," +
                    " (((bank).worker).worker).lastname as worker_lastname," +
                    " (((bank).worker).worker).phonenumber as worker_phonenumber," +
                    " (((bank).worker).worker).email as worker_email," +
                    " ((bank).worker).work_position as worker_position," +
                    " ((bank).worker).code as worker_code," +
                    " (((bank).client).client).firstname as client_firstname," +
                    " (((bank).client).client).lastname as client_lastname," +
                    " (((bank).client).client).phonenumber as client_phonenumber," +
                    " (((bank).client).client).email as client_email," +
                    " (((bank).client).account).account_number as client_account_number," +
                    " (((bank).client).account).balance as client_balance" +
                    " FROM banks_type");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Bank bank = new Bank(rs.getLong("id"), rs.getString("bank_name"));

                BankAccount bankAccount = new BankAccount(rs.getLong("client_account_number"));
                bankAccount.increaseBalance(rs.getInt("client_balance"));
                Client client = new Client(
                        rs.getString("client_firstname"),
                        rs.getString("client_lastname"),
                        rs.getString("client_phoneNumber"),
                        rs.getString("client_email")
                );
                client.setBankAccount(bankAccount);

                Worker worker = new Worker(
                        rs.getString("worker_firstname"),
                        rs.getString("worker_lastname"),
                        rs.getString("worker_phoneNumber"),
                        rs.getString("worker_email"),
                        rs.getString("worker_position"),
                        rs.getString("worker_code")
                );

                bank.setClient(client);
                bank.setWorker(worker);

                result.add(bank);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteBankByName(String name) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM banks_type WHERE (bank).bank_name = ?");
            statement.setString(1, name);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bank addBank(Bank bank) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into banks_type (" +
                            "bank.bank_name," +
                            " bank.worker.worker.firstname," +
                            " bank.worker.worker.lastname," +
                            " bank.worker.worker.phonenumber," +
                            " bank.worker.worker.email," +
                            " bank.worker.work_position," +
                            " bank.worker.code," +
                            " bank.client.client.firstname," +
                            " bank.client.client.lastname," +
                            " bank.client.client.phonenumber," +
                            " bank.client.client.email," +
                            " bank.client.account.account_number," +
                            " bank.client.account.balance) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, bank.getName());
            statement.setString(2, bank.getWorker().getFirstName());
            statement.setString(3, bank.getWorker().getLastName());
            statement.setString(4, bank.getWorker().getPhoneNumber());
            statement.setString(5, bank.getWorker().getEmail());
            statement.setString(6, bank.getWorker().getPosition());
            statement.setString(7, bank.getWorker().getCode());
            statement.setString(8, bank.getClient().getFirstName());
            statement.setString(9, bank.getClient().getLastName());
            statement.setString(10, bank.getClient().getPhoneNumber());
            statement.setString(11, bank.getClient().getEmail());
            statement.setLong(12, bank.getClient().getBankAccount().getAccountNumber());
            statement.setInt(13, bank.getClient().getBankAccount().getBalance());

            statement.executeUpdate();

            ResultSet generatedKey = statement.getGeneratedKeys();
            if (generatedKey.next()) {
                bank.setId(generatedKey.getLong(1));
            } else {
                throw new SQLException("Creating bank failed");
            }

            return bank;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateBank(Bank bank) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "update banks_type set " +
                            " bank.worker.worker.firstname = ?," +
                            " bank.worker.worker.lastname = ?," +
                            " bank.worker.worker.phonenumber = ?," +
                            " bank.worker.worker.email = ?," +
                            " bank.worker.work_position = ?," +
                            " bank.worker.code = ?," +
                            " bank.client.client.firstname = ?," +
                            " bank.client.client.lastname = ?," +
                            " bank.client.client.phonenumber = ?," +
                            " bank.client.client.email = ?," +
                            " bank.client.account.account_number = ?," +
                            " bank.client.account.balance = ? where (bank).bank_name=?");
            statement.setString(1, bank.getWorker().getFirstName());
            statement.setString(2, bank.getWorker().getLastName());
            statement.setString(3, bank.getWorker().getPhoneNumber());
            statement.setString(4, bank.getWorker().getEmail());
            statement.setString(5, bank.getWorker().getPosition());
            statement.setString(6, bank.getWorker().getCode());
            statement.setString(7, bank.getClient().getFirstName());
            statement.setString(8, bank.getClient().getLastName());
            statement.setString(9, bank.getClient().getPhoneNumber());
            statement.setString(10, bank.getClient().getEmail());
            statement.setLong(11, bank.getClient().getBankAccount().getAccountNumber());
            statement.setInt(12, bank.getClient().getBankAccount().getBalance());
            statement.setString(13, bank.getName());

            statement.executeUpdate();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
