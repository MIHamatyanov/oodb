package lab6;

import lab6.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Lab6Main {
    public static String PATH_FOR_SCAN = "lab6.model";

    public static void main(String[] args) {
        System.out.println("STEP 1: scan package " + PATH_FOR_SCAN + ":");

        List<Class<?>> classList = PathScan.find(PATH_FOR_SCAN);
        classList.forEach(c -> System.out.println("\t" + c.getCanonicalName()));

        System.out.println("STEP 2: scan class fields:");
        for (Class<?> c : classList) {
            System.out.println("\tFields of class " + c.getName());
            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("\t\t" + field.getName());
            }
        }

        System.out.println("STEP 3: scan class methods:");
        for (Class<?> c : classList) {
            System.out.println("\tFields of class " + c.getName());
            Method[] methods = c.getMethods();
            for (Method method : methods) {
                System.out.println("\t\t" + method.getName());
            }
        }

        System.out.println("STEP 4: scan class annotations: ");
        for (Class<?> c : classList) {
            System.out.println("\tAnnotations of class " + c.getName());
            Annotation[] annotations = c.getAnnotations();
            if (annotations != null) {
                for (Annotation a : annotations) {
                    if (a.annotationType().equals(Entity.class)) {
                        System.out.println("\t\t" + c.getSimpleName() + " is entity!");
                    }
                }
            }
        }

        System.out.println("STEP 5: scan fields annotations:");
        for (Class<?> c : classList) {
            System.out.println("\tField annotations of class " + c.getName());
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                Annotation[] annotations = f.getAnnotations();
                for (Annotation a : annotations) {
                    if (a.annotationType().equals(Column.class)) {
                        System.out.println(String.format("\t\tField %s %s is attribute!", f.getType().getName(), f.getName()));
                    }

                    if (a.annotationType().equals(ManyToOne.class)) {
                        String relationClass = f.getGenericType().getTypeName();
                        System.out.println(String.format("\t\tClass %s in relation ManyToOne with class %s", c.getSimpleName(), relationClass.substring(relationClass.indexOf("<") + 1, relationClass.indexOf(">"))));
                    }

                    if (a.annotationType().equals(OneToMany.class)) {
                        String relationClass = f.getGenericType().getTypeName();
                        System.out.println(String.format("\t\tClass %s in relation OneToMany with class %s", c.getSimpleName(), relationClass.substring(relationClass.indexOf("<") + 1, relationClass.indexOf(">"))));
                    }

                    if (a.annotationType().equals(OneToOne.class)) {
                        String relationClass = f.getGenericType().getTypeName();
                        System.out.println(String.format("\t\tClass %s in relation OneToOne with class %s", c.getSimpleName(), relationClass.substring(relationClass.indexOf("<") + 1, relationClass.indexOf(">"))));
                    }
                }
            }
        }

        System.out.println("STEP 6: get superclass:");
        for (Class<?> c : classList) {
            Class superClass = c.getSuperclass();
            if (!superClass.equals(Object.class)) {
                System.out.println("\tSuper class of " + c.getSimpleName() + " is " + superClass.getSimpleName());
            }
        }
    }
}
