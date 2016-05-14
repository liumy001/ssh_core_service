package com.liumy.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.liumy.entity.Message;

@Repository
public class WeChatMessageDao {
	
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public Integer save(Message weChatMessage)throws Exception{
		
		 sessionFactory.getCurrentSession().save(weChatMessage);
		 
		 return weChatMessage.getId();
	}

}
