package lab1;

class BankAccount {
    private Long id;
    public static long currentAccountNumber = 1000000000000000L;
    private long accountNumber;
    private int balance;

    BankAccount(long accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    long getAccountNumber() {
        return accountNumber;
    }

    int getBalance() {
        return balance;
    }

    void increaseBalance(int cash) {
        this.balance += cash;
    }

    boolean reduceBalance(int cash) {
        if (balance < cash) {
            return false;
        } else {
            this.balance -= cash;
            return true;
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", balance=" + balance +
                '}';
    }
}
