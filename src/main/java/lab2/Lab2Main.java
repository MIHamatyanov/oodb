package lab2;

import lab1.Bank;
import lab1.Worker;

import java.util.List;

public class Lab2Main {
    public static void main(String[] args) {
        List<Bank> banks = FileWorker.load();

        banks.forEach(System.out::println);

        Bank bank = BankService.findBankByName(banks, "ВТБ");

        if (bank != null) {
            Worker worker = new Worker("Wor", "ker", "+71231231212", "worker@mail.ru", "programmer", "010101");
            bank.getWorkers().add(worker);
        }

        BankService.sortByName(banks).forEach(System.out::println);

        FileWorker.save(banks);

    }
}
