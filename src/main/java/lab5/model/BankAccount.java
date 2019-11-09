package lab5.model;

public class BankAccount {
    static long currentAccountNumber = 1000000000000000L;
    private long accountNumber;
    private int balance;

    public BankAccount(long accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void increaseBalance(int cash) {
        this.balance += cash;
    }

    public boolean reduceBalance(int cash) {
        if (balance < cash) {
            return false;
        } else {
            this.balance -= cash;
            return true;
        }
    }

    @Override
    public String toString() {
        return "  BankAccount {" +
                ",\n         accountNumber=" + accountNumber +
                ",\n         balance=" + balance +
                "\n     }";
    }
}
