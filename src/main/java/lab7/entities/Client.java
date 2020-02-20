package lab7.entities;

import lab7.annotation.*;

import java.util.List;

@Entity
public class Client extends Person{
    @Id
    private Long id;
    @Column
    @ManyToOne
    private Bank bank;
    @Column
    @OneToMany
    private List<BankAccount> bankAccounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
