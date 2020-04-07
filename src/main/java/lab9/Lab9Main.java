package lab9;

import lab9.entities.Bank;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Lab9Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab9");
        EntityManager entityManager = emf.createEntityManager();

        Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("ВТБ");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(bank);
        transaction.commit();

        entityManager.close();
        emf.close();
    }
}
