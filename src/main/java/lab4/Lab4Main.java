package lab4;

import lab1.Bank;
import lab1.Worker;

import java.util.ArrayList;
import java.util.List;

public class Lab4Main {
    public static void main(String[] args) {
        List<Bank> banks = Lab4Service.loadJson();

        if (banks != null) {
            for (Bank bank : banks) {
                System.out.println(bank);
            }
        }

        System.out.println("-------------------");

        banks = Lab4Service.loadJsonb();

        if (banks != null) {
            for (Bank bank : banks) {
                System.out.println(bank);
            }
        }

        Bank bank = Lab4Service.loadByName("ВТБ");

        if (bank != null) {
            Worker worker = new Worker("Работник", "Рабочий", "+71231231212", "email@mail.ru", "manager", "0101");
            bank.addWorker(worker);
            bank.setName("Альфа банк");
            bank.setId(5L);

            Lab4Service.save(new ArrayList<Bank>() {{
                add(bank);
            }});
        }
    }
}
