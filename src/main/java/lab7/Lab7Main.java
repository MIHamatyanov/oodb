package lab7;

import lab7.annotation.Entity;
import lab7.init.EntityScan;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lab7Main {
    public static void main(String[] args) {
        /*DBScan scanner = new DBScan();
        System.out.println("Tables: ");
        scanner.getTables().forEach(table -> {
            System.out.println("\t" + table);
            System.out.println("\tFields: ");
            scanner.getField(table).forEach(field -> {
                System.out.println("\t\t" + field);
            });
        });*/

        EntityScan scanner = new EntityScan();
        String scanPath = "lab7.entities";
        List<Class<?>> classes = scanner.find(scanPath);
        classes.forEach(c -> {
            if (c.getAnnotation(Entity.class) != null) {
                System.out.println(c.getSimpleName());
                List<Field> fields = new ArrayList<>(Arrays.asList(c.getDeclaredFields()));
                if (!c.getSuperclass().equals(Object.class)) {
                    fields.addAll(Arrays.asList(c.getSuperclass().getDeclaredFields()));
                }
                for (Field field : fields) {
                    System.out.println("\t" + field.getType());
                }
            }
        });
    }
}
