package com.hibernate.dao;

import com.hibernate.entity.Manufacture; // Import Manufacture
import com.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ManufactureDAO {

    public boolean add(Manufacture m) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(m);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Manufacture get(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Manufacture.class, id);
        }
    }

    public List<Manufacture> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Manufacture", Manufacture.class).list();
        }
    }

    public boolean remove(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Manufacture m = session.get(Manufacture.class, id);
            if (m != null) {
                session.delete(m);
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

    public boolean update(Manufacture m) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(m);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Additional methods
    public boolean allManufacturersHaveMoreThan100Employees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Manufacture WHERE employee <= 100", Long.class);
            return query.uniqueResult() == 0;
        }
    }

    public int sumAllEmployees() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT SUM(employee) FROM Manufacture", Long.class);
            return query.uniqueResult() != null ? query.uniqueResult().intValue() : 0;
        }
    }

    public Manufacture getLastManufacturerBasedInUS() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Manufacture> query = session.createQuery("FROM Manufacture WHERE location = 'US'", Manufacture.class);
            List<Manufacture> manufacturers = query.list();
            if (manufacturers.isEmpty()) {
                throw new IllegalStateException("No manufacturers based in the US found");
            }
            return manufacturers.get(manufacturers.size() - 1);
        }
    }
}
