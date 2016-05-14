package com.liumy.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liumy.dao.UsersDAO;
import com.liumy.entity.Users;

@Service("userService")
@Transactional
public class UserService {
	@Resource
	private UsersDAO userDao;

	
	public int userCount() {
		return userDao.getAllUser().size();
	}

	public UsersDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UsersDAO userDao) {
		this.userDao = userDao;
	}
	
	public void save(Users user){
		userDao.save(user);
	}

}
