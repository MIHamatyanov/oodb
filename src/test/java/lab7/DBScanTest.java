package lab7;

import lab7.annotation.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class DBScanTest {

    @Test
    public void scanTablesTest() {
        DBScan dbScanner = new DBScan();
        EntityScan pathScanner = new EntityScan();

        HashMap<String, HashSet<String>> tables = new HashMap<>();
        dbScanner.getTables().forEach(table -> {
            HashSet<String> fields = new HashSet<>(dbScanner.getField(table));
            tables.put(table, fields);
        });

        HashMap<String, HashSet<String>> classes = new HashMap<>();
        pathScanner.find("lab7.entities").forEach(c -> {
            if (c.getAnnotation(Entity.class) != null) {
                HashSet<String> fieldNames = new HashSet<>();
                for (Field field : c.getDeclaredFields()) {
                    if (field.getAnnotation(ManyToOne.class) != null || field.getAnnotation(OneToOne.class) != null) {
                        fieldNames.add(field.getName().toLowerCase().concat("_id"));
                    } else if (field.getAnnotation(OneToMany.class) != null || field.getAnnotation(ManyToMany.class) != null) {
                        String relationTypeName = field.getGenericType().getTypeName();
                        String relationClassName = relationTypeName.substring(relationTypeName.indexOf("<") + 1, relationTypeName.indexOf(">"));
                        String className = relationClassName.substring(relationClassName.lastIndexOf('.') + 1);

                        String interTableName = c.getSimpleName().toLowerCase() + "_" + className.toLowerCase();
                        HashSet<String> interFields = new HashSet<>();
                        interFields.add(c.getSimpleName().toLowerCase() + "_id");
                        interFields.add(field.getName().toLowerCase() + "_id");
                        classes.put(interTableName, interFields);
                    } else {
                        fieldNames.add(field.getName().toLowerCase());
                    }
                }

                if (!c.getSuperclass().equals(Object.class)) {
                    for (Field field : c.getSuperclass().getDeclaredFields()) {
                        if (field.getAnnotation(ManyToOne.class) != null || field.getAnnotation(OneToOne.class) != null) {
                            fieldNames.add(field.getName().toLowerCase().concat("_id"));
                        } else if (field.getAnnotation(OneToMany.class) != null || field.getAnnotation(ManyToMany.class) != null) {
                            String interTableName = c.getSimpleName().toLowerCase() + "_" + field.getName().toLowerCase();
                            HashSet<String> interFields = new HashSet<>();
                            interFields.add(c.getSimpleName().toLowerCase() + "_id");
                            interFields.add(field.getName().toLowerCase() + "_id");
                            classes.put(interTableName, interFields);
                        } else {
                            fieldNames.add(field.getName().toLowerCase());
                        }
                    }
                }

                classes.put(c.getSimpleName().toLowerCase(), fieldNames);
            }
        });

        /*for (Map.Entry<String, HashSet<String>> entry : classes.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(f -> System.out.println("\t" + f));
        }

        System.out.println("---------------");

        for (Map.Entry<String, HashSet<String>> entry : tables.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(f -> System.out.println("\t" + f));
        }*/

        for (Map.Entry<String, HashSet<String>> entry : classes.entrySet()) {
            String className = entry.getKey();
            HashSet<String> classFields = entry.getValue();

            assertTrue(tables.containsKey(className));
            HashSet<String> tableFields = classes.get(className);
            for (String fieldName : classFields) {
                assertTrue(tableFields.contains(fieldName));
            }
        }
    }
}
