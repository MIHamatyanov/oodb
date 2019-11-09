package lab5.model;

public class Client extends Person {
    private BankAccount bankAccount;

    public Client(String firstName, String lastName, String phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
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

        bankAccount = new BankAccount(newAccountNumber);

        return newAccountNumber;
    }

    private BankAccount findBankAccount(long accountNumber) {
        return bankAccount.getAccountNumber() == accountNumber ? bankAccount : null;
    }

    @Override
    public String toString() {
        return " Client {" +
                super.toString() +
                ",\n     bankAccount=" + bankAccount +
                "\n }";
    }
}
