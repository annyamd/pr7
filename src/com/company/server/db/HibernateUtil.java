package com.company.server.db;

import com.company.server.model.Coordinates;
import com.company.server.model.MusicBand;
import com.company.server.model.MusicGenre;
import com.company.server.model.Studio;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory = new Configuration().configure(new File("hibernate.cfg.xml"))
                    .addAnnotatedClass(MusicBand.class)
                    .addAnnotatedClass(Coordinates.class)
                    .addAnnotatedClass(Studio.class)
                    .addAnnotatedClass(MusicGenre.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(MusicBandEntity.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
