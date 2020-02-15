package lab7.entities;

import lab7.annotation.Column;
import lab7.annotation.Entity;
import lab7.annotation.Id;
import lab7.annotation.ManyToOne;

@Entity
public class BankAccount {
    @Id
    private Long id;
    @Column
    private long accountNumber;
    @Column
    private long balance;
    @Column
    @ManyToOne
    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
