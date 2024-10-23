package com.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Tạo đối tượng Configuration và thiết lập file hibernate.cfg.xml
            Configuration configuration = new Configuration().configure();
            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            // Nếu có lỗi xảy ra, in ra lỗi và ném ra ngoại lệ
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Đóng SessionFactory khi không còn sử dụng
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
