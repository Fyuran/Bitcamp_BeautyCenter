package com.bitcamp.centro.estetico.controller;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

class HibernateUtils {

    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if(factory != null) {
                factory.close();
            }
            throw new ExceptionInInitializerError(e);
        }
    }

    static SessionFactory getSessionFactory() {
        return factory;
    }

}
