package lab3.model;

import javax.xml.bind.annotation.XmlElement;

public class BankAccount {
    private Long id;
    static long currentAccountNumber = 1000000000000000L;
    private long accountNumber;
    private int balance;

    public BankAccount() {
    }

    BankAccount(long accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getCurrentAccountNumber() {
        return currentAccountNumber;
    }

    public static void setCurrentAccountNumber(long currentAccountNumber) {
        BankAccount.currentAccountNumber = currentAccountNumber;
    }

    @XmlElement(name = "accountNumber")
    long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @XmlElement(name = "balance")
    int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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
