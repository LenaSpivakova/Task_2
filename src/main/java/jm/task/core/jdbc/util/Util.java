package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
//    public static final String DRIVER_KEY = "db.driver";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .addAnnotatedClass(User.class)
                        .setProperty("hibernate.connection.driver_class", PropertiesUtil.get("db.driver"))
                        .setProperty("hibernate.connection.url", PropertiesUtil.get("db.url"))
                        .setProperty("hibernate.connection.username", PropertiesUtil.get("db.username"))
                        .setProperty("hibernate.connection.password", PropertiesUtil.get("db.password"))
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                        .setProperty("hibernate.show_sql", "true");

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USERNAME_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных SQL: ", e);
        }
    }
}