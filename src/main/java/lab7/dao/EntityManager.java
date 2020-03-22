package lab7.dao;

public interface EntityManager {
    void persist(Object o);

    <T> T merge(T o);

    void remove(Object o);

    <T> T find(Class<T> o, Object pk);

    void refresh(Object o);
}
