package com.crawler;

import org.hibernate.SessionFactory;

import com.crawler.db.BaseModel;

public class ThreadSession extends ThreadLocal<SessionFactory> {

	@Override
	protected SessionFactory initialValue() {
		return BaseModel.getSessionFactory();
	}


}
