package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.DataSource;
import model.DataSourceColumnHeader;
import model.DataSourceRowData;
import model.User;


public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
            	  Configuration configuration = new Configuration();
            	  configuration.configure("hibernate.cfg.xml").
            			addAnnotatedClass(DataSource.class).
            			addAnnotatedClass(DataSourceRowData.class).
            			addAnnotatedClass(DataSourceColumnHeader.class).
            			addAnnotatedClass(User.class);
            	
            	sessionFactory = configuration.buildSessionFactory();
            	
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
	}
}
