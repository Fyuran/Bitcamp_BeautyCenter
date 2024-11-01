package com.bitcamp.centro.estetico.DAO;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

class HibernateUtils {

	//XML based configuration
	private static SessionFactory sessionFactory;

    private HibernateUtils(){
		sessionFactory = getSessionFactory();
	}
    private static class SingletonHelper {
        private static final HibernateUtils INSTANCE = new HibernateUtils();
    }
	public static HibernateUtils getInstance() {
		return SingletonHelper.INSTANCE;
	}

    static SessionFactory buildSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }

        return sessionFactory;
    }

	void tearDown() throws Exception {
		if ( sessionFactory != null ) {
			sessionFactory.close();
		}
	}

    static SessionFactory getSessionFactory() {
		if(sessionFactory == null) return buildSessionFactory();
        return sessionFactory;
    }

}
