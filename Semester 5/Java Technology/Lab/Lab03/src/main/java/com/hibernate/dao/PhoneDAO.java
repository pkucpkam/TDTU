package com.hibernate.dao;

import com.hibernate.entity.Manufacture;
import com.hibernate.entity.Phone; // Import Phone
import com.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PhoneDAO {

    public boolean add(Phone p) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(p);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Phone get(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Phone.class, id);
        }
    }

    public List<Phone> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Phone", Phone.class).list();
        }
    }

    public boolean remove(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Phone p = session.get(Phone.class, id);
            if (p != null) {
                session.delete(p);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Phone p) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(p);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods
    public Phone getPhoneWithHighestPrice() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Phone> query = session.createQuery("FROM Phone ORDER BY price DESC", Phone.class);
            return query.setMaxResults(1).uniqueResult();
        }
    }

    public List<Phone> getPhonesSortedByCountry() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Phone ORDER BY country, price DESC", Phone.class).list();
        }
    }

    public boolean hasPhoneAbove50Million() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Phone WHERE price > 50000000", Long.class);
            return query.uniqueResult() > 0;
        }
    }

    public Phone getFirstPinkPhoneAbove15Million() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Phone> query = session.createQuery("FROM Phone WHERE color = 'Pink' AND price > 15000000", Phone.class);
            return query.setMaxResults(1).uniqueResult();
        }
    }
}