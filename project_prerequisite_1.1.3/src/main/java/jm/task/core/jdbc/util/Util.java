package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Util {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "Bishkek#312";
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest";

    private Util() {
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return connection;
    }

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry standardServiceRegistry;

    static {
        try {
            if (sessionFactory == null) {
                StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();

                Map<String, String> dbSettings = new HashMap<>();
                dbSettings.put(Environment.URL, URL);
                dbSettings.put(Environment.USER, USERNAME);
                dbSettings.put(Environment.PASS, PASSWORD);
                dbSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                dbSettings.put(Environment.HBM2DDL_AUTO, "update");

                standardServiceRegistryBuilder.applySettings(dbSettings);
                standardServiceRegistry = standardServiceRegistryBuilder.build();

                MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
                metadataSources.addAnnotatedClass(User.class);
                Metadata metadata = metadataSources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (standardServiceRegistry != null) {
                StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
