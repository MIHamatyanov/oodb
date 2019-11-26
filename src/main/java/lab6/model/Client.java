package lab6.model;

import lab6.annotations.Column;
import lab6.annotations.Entity;
import lab6.annotations.OneToMany;

import java.util.List;

@Entity
public class Client extends Person {
    @Column
    private Long id;
    @OneToMany
    @Column
    private List<BankAccount> bankAccounts;

    public Client(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public int getBankAccountBalance(long accountNumber) {
        BankAccount bankAccount = findBankAccount(accountNumber);
        if (bankAccount != null) {
            return bankAccount.getBalance();
        }

        return 0;
    }

    public void putMoney(int cash, long accountNumber) {
        BankAccount bankAccount = findBankAccount(accountNumber);
        if (bankAccount != null) {
             bankAccount.increaseBalance(cash);
        }
    }

    public boolean withdrawMoney(int cash, long accountNumber) {
        BankAccount bankAccount = findBankAccount(accountNumber);
        if (bankAccount != null) {
            return bankAccount.reduceBalance(cash);
        }

        return false;
    }

    public long createBankAccount() {
        long newAccountNumber = BankAccount.currentAccountNumber++;

        bankAccounts.add(new BankAccount(newAccountNumber));

        return newAccountNumber;
    }

    private BankAccount findBankAccount(long accountNumber) {
        for (BankAccount bankAccount : bankAccounts) {
            if (bankAccount.getAccountNumber() == accountNumber) {
                return bankAccount;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                super.toString() +
                ", bankAccounts=" + bankAccounts +
                '}';
    }
}
