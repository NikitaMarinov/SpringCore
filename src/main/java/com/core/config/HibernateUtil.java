package com.core.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@org.springframework.context.annotation.Configuration
public class HibernateUtil {
    public SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
