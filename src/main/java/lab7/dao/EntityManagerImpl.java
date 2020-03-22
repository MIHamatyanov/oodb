package lab7.dao;

import lab7.annotation.*;
import lab7.entities.Bank;
import lab7.entities.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;

public class EntityManagerImpl implements EntityManager {
    private Connection connection;
    private Properties dbProperties;

    public EntityManagerImpl(Properties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public void persist(Object o) {
        String tableName = o.getClass().getSimpleName().toLowerCase();
        HashMap<String, Object> fieldsValues = new HashMap<>();

        if (!o.getClass().getSuperclass().equals(Object.class)) {
            HashMap<String, Object> parentFields = getFieldsValues(o, o.getClass().getSuperclass().getDeclaredFields());
            fieldsValues.putAll(parentFields);
        }
        fieldsValues.putAll(getFieldsValues(o, o.getClass().getDeclaredFields()));

        Connection connection = null;
        try {
            connection = getConnection();
            String sql_insert = "insert into " + tableName + "(";

            String keys = Arrays.toString(fieldsValues.keySet().toArray());
            keys = keys.substring(1, keys.length() - 1);
            sql_insert = sql_insert.concat(keys).concat(") values (");

            String values = fieldsValues.values().stream().map(v -> "'" + v + "'").collect(Collectors.joining(","));
            sql_insert = sql_insert.concat(values).concat(")");

            PreparedStatement statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Method setIdMethod = o.getClass().getMethod("setId", Long.class);
                setIdMethod.invoke(o, generatedKeys.getLong(1));
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    private HashMap<String, Object> getFieldsValues(Object o, Field[] fields) {
        HashMap<String, Object> result = new HashMap<>();
        Arrays.stream(fields).filter(f -> f.getAnnotation(Column.class) != null).forEach(field -> {
            String getMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            try {
                Method method = o.getClass().getMethod(getMethodName);
                if (field.getAnnotation(OneToOne.class) != null || field.getAnnotation(ManyToOne.class) != null) {
                    Object fieldObject = method.invoke(o);
                    if (fieldObject != null) {
                        Method getIdMethod = fieldObject.getClass().getMethod("getId");
                        Object id = getIdMethod.invoke(fieldObject);

                        if (id == null) {
                            persist(fieldObject);
                            id = getIdMethod.invoke(fieldObject);
                        }

                        result.put(field.getName().toLowerCase() + "_id", id);
                    }
                } else if (field.getAnnotation(ManyToMany.class) != null || field.getAnnotation(OneToMany.class) != null) {
                    // TODO: 13.03.2020 Доделать
                } else {
                    result.put(field.getName().toLowerCase(), method.invoke(o));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        return result;
    }

    @Override
    public <T> T merge(T o) {
        String tableName = o.getClass().getSimpleName().toLowerCase();
        HashMap<String, Object> fieldsValues = new HashMap<>();

        if (!o.getClass().getSuperclass().equals(Object.class)) {
            fieldsValues.putAll(getFieldsValues(o, o.getClass().getSuperclass().getDeclaredFields()));
        }
        fieldsValues.putAll(getFieldsValues(o, o.getClass().getDeclaredFields()));

        Connection connection = null;
        try {
            connection = getConnection();
            String sql_update = "update " + tableName + " set ";
            String keyValue = fieldsValues.entrySet().stream().map(entry -> entry.getKey().concat("='").concat(entry.getValue().toString()).concat("'")).collect(Collectors.joining(", "));
            sql_update = sql_update.concat(keyValue);

            Method getIdMethod = o.getClass().getMethod("getId");
            String id = getIdMethod.invoke(o).toString();
            sql_update = sql_update.concat(" where id = ").concat(id);

            PreparedStatement statement = connection.prepareStatement(sql_update);
            int rows = statement.executeUpdate();

            if (rows != 0) {
                return (T) find(o.getClass(), id);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return o;
    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public <T> T find(Class<T> o, Object pk) {
        String tableName = o.getSimpleName();

        String sql = "select * from " + tableName + " where id = " + pk.toString();

        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                T object = o.newInstance();

                Method setIdMethod = o.getMethod("setId", Long.class);
                setIdMethod.invoke(object, rs.getLong("id"));

                Arrays.stream(o.getDeclaredFields()).filter(field -> field.getAnnotation(Column.class) != null).forEach(field -> {
                    String setMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                    try {
                        Method method = o.getMethod(setMethodName, field.getType());

                        if (field.getAnnotation(OneToOne.class) != null || field.getAnnotation(ManyToOne.class) != null) {
                            Object fieldObject = find(field.getType(), rs.getObject(field.getName().toLowerCase() + "_id"));
                            method.invoke(o, fieldObject);
                        } else if (field.getAnnotation(ManyToMany.class) != null || field.getAnnotation(OneToMany.class) != null) {
                            // TODO: 13.03.2020 Доделать
                        } else {
                            Object object1 = rs.getObject(field.getName().toLowerCase());

                            method.invoke(o, field.getType().cast(object1));
                        }
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | SQLException e) {
                        e.printStackTrace();
                    }
                });

                return object;
            }
        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        return null;
    }

    @Override
    public void refresh(Object o) {

    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    this.dbProperties.getProperty("url"),
                    this.dbProperties.getProperty("user"),
                    this.dbProperties.getProperty("password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties dbProperties = new Properties();
        dbProperties.put("url", "jdbc:postgresql://localhost:5432/oodb");
        dbProperties.put("user", "postgres");
        dbProperties.put("password", "12345");

        EntityManager entityManager = new EntityManagerImpl(dbProperties);

        Bank bank = new Bank();
        bank.setId(7L);
        bank.setName("TestBank");

        Client client = new Client();
        client.setId(1L);
        client.setFirstName("qweqName");
        client.setLastName("LastName");
        client.setEmail("email@mail.ru");
        client.setPhoneNumber("89001511223");
        client.setBank(bank);

        entityManager.find(bank.getClass(), 7L);
    }
}
