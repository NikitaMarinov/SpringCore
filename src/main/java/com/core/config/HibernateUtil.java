package com.core.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
