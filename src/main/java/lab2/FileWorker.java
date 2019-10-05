package lab2;

import com.google.gson.Gson;
import lab1.Bank;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class FileWorker {
    private static final File FILE = new File("src/main/java/lab2/banks.json");

    public static List<Bank> load() {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            Bank[] banks = new Gson().fromJson(reader, Bank[].class);

            return Arrays.asList(banks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(List<Bank> banks) {
        String banksAsJson = new Gson().toJson(banks);

        try (OutputStream output = new FileOutputStream(FILE)) {
            output.write(banksAsJson.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
