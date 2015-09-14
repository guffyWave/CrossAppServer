package share.apk.android.management;

import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class DatabaseConnectionManager {
	private static volatile DatabaseConnectionManager databaseConnectionManager;
	private SessionFactory sessionFactory;
	private Session session;

	static Logger logger = Logger.getLogger(DatabaseConnectionManager.class
			.getName());

	private DatabaseConnectionManager() throws HibernateException {
		// Configuration configuration = new Configuration().configure();
		// StandardServiceRegistryBuilder builder = new
		// StandardServiceRegistryBuilder()
		// .applySettings(configuration.getProperties());
		// sessionFactory = configuration.buildSessionFactory(builder.build());
	}

	public static DatabaseConnectionManager getInstance() {
		if (databaseConnectionManager == null) {
			synchronized (DatabaseConnectionManager.class) {
				if (databaseConnectionManager == null) {
					databaseConnectionManager = new DatabaseConnectionManager();
					System.out
							.println("New APKShare Database Connection object created ");
					logger.info("New APKShare Database Connection object created ");
				}
			}
		} else {
			System.out
					.println("Old APKShare Database Connectionobject returned ");
			logger.info("Old APKShare Database Connectionobject returned  ");
		}

		return databaseConnectionManager;
	}

	public Session getSession() {
		session = sessionFactory.openSession();
		return session;
	}

}
