package lab3.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private Long id;
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public Client() {
    }

    public Client(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElementWrapper(name = "bankAccounts")
    @XmlElement(name = "bankAccount")
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
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
