package com.taot.example;

import java.math.BigDecimal;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.taot.example.entity.Account;
import com.taot.example.entity.Position;
import com.taot.example.entity.SecurityPosition;

public class App {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        System.out.println("Starting...");
        sessionFactory = createSessionFactory();

//        loadAccount();
//        saveAccount();
//        deleteAccount();
        
        savePosition();
//        loadPosition();
    }

    private static void loadPosition() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

//        Query q = session.createQuery("from Position");
//        for (Object o : q.list()) {
//            System.out.println(o);
//        }
        Position p = (Position)session.load(Position.class, 1L);
        System.out.println(p);

        tx.commit();
        sessionFactory.close();
    }
    
    private static void savePosition() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Position p = new SecurityPosition(1L, "GOOG", new BigDecimal(1000), new BigDecimal(100));
        session.saveOrUpdate(p);
        
//        Position p = new CashPosition(1L, new BigDecimal(50000));
//        session.saveOrUpdate(p);

        tx.commit();
        sessionFactory.close();
    }

    private static void deleteAccount() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query q = session.createQuery("from Account cat order by Id desc");
        q.setMaxResults(1);
        for (Object o : q.list()) {
            Account a = (Account)o;
            session.delete(a);
            System.out.println("Delete: " + a);
        }

        tx.commit();
        sessionFactory.close();
    }

    private static void saveAccount() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Account acc = new Account("DataYes", new BigDecimal(4000));
        session.saveOrUpdate(acc);

        tx.commit();
        sessionFactory.close();
    }

    private static void loadAccount() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        //Account acc = (Account) session.load(Account.class, 1L);
        //System.out.println("Account = " + acc);
        
//        Query q = session.createQuery("from Account cat where Owner = :name");
//        q.setString("name", "DataYes");
        Query q = session.createQuery("from Account cat order by Id asc");
        q.setMaxResults(1);
        for (Object o : q.list()) {
            Account a = (Account)o;
            System.out.println(a);
        }

        tx.commit();
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
