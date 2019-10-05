package lab2;

import lab1.Bank;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BankService {
    public static Bank findBankByName(List<Bank> banks, String name) {
        return banks.stream().filter(bank -> bank.getName().equals(name)).findFirst().orElse(null);
    }

    public static List<Bank> sortByName(List<Bank> banks) {
        return banks.stream().sorted(Comparator.comparing(Bank::getName)).collect(Collectors.toList());
    }
}
