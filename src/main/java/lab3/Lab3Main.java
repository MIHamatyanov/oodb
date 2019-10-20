package lab3;

import lab3.model.Bank;
import lab3.model.Collection;
import lab3.model.Worker;

import java.util.List;

public class Lab3Main {
    public static void main(String[] args) {
        Collection collection = Lab3Service.load();

        if (collection != null) {
            printBanks(collection.getBanks());
        }

        System.out.println("-------------------------");

        if (collection != null) {
            List<Bank> sortedBanks = Lab3Service.sortByName(collection.getBanks());
            printBanks(sortedBanks);
        }

        System.out.println("-------------------------");

        if (collection != null) {
            Bank bank = Lab3Service.findBankByName(collection.getBanks(), "ВТБ");
            Worker worker = new Worker("Worker", "2", "+88005553535", "gmail@gmail.com", "Boss", "-1");
            bank.addWorker(worker);

            Lab3Service.save(collection);
        }

    }

    private static void printBanks(List<Bank> banks) {
        for (Bank b : banks) {
            System.out.println(b);
        }
    }
}
