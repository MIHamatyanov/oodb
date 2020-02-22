package lab7.init;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EntityScan {
    public List<Class<?>> find(String packageToScan) {
        String pathToScan = packageToScan.replaceAll("\\.", "/");
        URL scanUrl = Thread.currentThread().getContextClassLoader().getResource(pathToScan);

        if (scanUrl == null) {
            throw new IllegalArgumentException("Bad package " + packageToScan);
        }

        File dirToScan = new File(scanUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : Objects.requireNonNull(dirToScan.listFiles())) {
            classes.addAll(find(file, packageToScan));
        }

        return classes;
    }

    private List<Class<?>> find(File file, String packageToScan) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = packageToScan + "." + file.getName();

        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(".class")) {
            String className = resource.substring(0, resource.length() - 6);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }

        return classes;
    }
}
