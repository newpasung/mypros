package com.crawler.db;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BaseModel implements Serializable{

	private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
			.configure("hibernate.cfg.xml").build();
	private static SessionFactory sessionFactory = new MetadataSources(registry)
			.buildMetadata().buildSessionFactory();
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Serializable save(){
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		try{
			return session.save(this);
		}finally {
			transaction.commit();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<BaseModel> findAll(Class classClause){
		Session session = getSessionFactory().openSession();
		try{
			return session.createQuery("from  "+classClause.getSimpleName()).getResultList();
		}finally {
			session.close();
		}
	}

}
