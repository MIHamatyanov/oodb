package lab6.model;

import lab6.annotations.Column;
import lab6.annotations.Entity;

@Entity
class BankAccount {
    @Column
    private Long id;
    static long currentAccountNumber = 1000000000000000L;
    @Column
    private long accountNumber;
    @Column
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
