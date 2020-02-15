package lab7;

import lab7.annotation.Entity;
import lab7.annotation.ManyToOne;
import lab7.annotation.OneToOne;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
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
                    } else {
                        fieldNames.add(field.getName().toLowerCase());
                    }
                }

                if (!c.getSuperclass().equals(Object.class)) {
                    for (Field field : c.getSuperclass().getDeclaredFields()) {
                        if (field.getAnnotation(ManyToOne.class) != null || field.getAnnotation(OneToOne.class) != null) {
                            fieldNames.add(field.getName().toLowerCase().concat("_id"));
                        } else {
                            fieldNames.add(field.getName().toLowerCase());
                        }
                    }
                }

                classes.put(c.getSimpleName().toLowerCase(), fieldNames);
            }
        });

        for (Map.Entry<String, HashSet<String>> entry : tables.entrySet()) {
            String tableName = entry.getKey();
            HashSet<String> tableFields = entry.getValue();

            assertTrue(classes.containsKey(tableName));
            HashSet<String> classFields = classes.get(tableName);
            assertEquals(tableFields.size(), classFields.size());
            for (String fieldName : tableFields) {
                assertTrue(classFields.contains(fieldName));
            }
        }
    }
}
