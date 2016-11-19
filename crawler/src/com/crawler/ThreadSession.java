package com.crawler;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.crawler.db.BaseModel;

public class ThreadSession extends ThreadLocal<SessionFactory> {

	@Override
	protected SessionFactory initialValue() {
		/*StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml").build();
		 SessionFactory sessionFactory = new MetadataSources(registry)
				.buildMetadata().buildSessionFactory();*/
		 return BaseModel.getSessionFactory();
	}


}
