package com.liumy.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.liumy.entity.UtilInfo;

@Repository
public class UtilInfoDao {

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public List<UtilInfo> findUtilInfoList(String content) {

		
		StringBuffer hql=new StringBuffer("from  util_info where isOk=0");
		
		if (content!=null&&!"all".equalsIgnoreCase(content)) {
			hql.append(" and ( utilName like '%"+content+"%'  ");
			hql.append(" or utilClass   like '%"+content+"%' ");
			hql.append(" or util_method   like '%"+content+"%' ");
			hql.append(" or utilDes   like '%"+content+"%' ");
			hql.append("  )");
		}
		Query query = sessionFactory.getCurrentSession().createQuery(
				hql.toString());
		List<UtilInfo> us = query.list();
		return us;
	}

}
