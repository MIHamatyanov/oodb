package lab5;

import lab5.model.Bank;
import lab5.model.Client;
import lab5.model.Worker;

import java.util.List;

public class Lab5Main {
    public static void main(String[] args) {
        Lab5Repository repo = new Lab5Repository();
        List<Bank> banks = repo.findAll();

        for (Bank bank : banks) {
            System.out.println(bank);
            System.out.println("----------------------------------------------------");
        }
        System.out.println("___________________________________________________________");

        repo.deleteBankByName("ВТБ");

        banks = repo.findAll();
        for (Bank bank : banks) {
            System.out.println(bank);
            System.out.println("----------------------------------------------------");
        }
        System.out.println("___________________________________________________________");

        Bank newBank = new Bank("ВТБ");

        Client client = new Client(
                "Хаматянов",
                "Марсель",
                "89005651212",
                "mail@gmail.com"
        );
        long accountNumber = client.createBankAccount();
        client.putMoney(102030, accountNumber);

        Worker worker = new Worker(
                "Worker",
                "Worker",
                "89009009090",
                "worker@mail.ru",
                "boss",
                "102030"
        );

        newBank.setClient(client);
        newBank.setWorker(worker);

        System.out.println(repo.addBank(newBank));

        System.out.println("___________________________________________________________");

        client.withdrawMoney(2800, accountNumber);

        repo.updateBank(newBank);

        banks = repo.findAll();
        for (Bank bank : banks) {
            System.out.println(bank);
            System.out.println("----------------------------------------------------");
        }
        System.out.println("___________________________________________________________");
    }
}
