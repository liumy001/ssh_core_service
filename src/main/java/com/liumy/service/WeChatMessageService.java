package com.liumy.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.liumy.dao.WeChatMessageDao;
import com.liumy.entity.Message;

@Service
@Transactional
public class WeChatMessageService {
    
	@Resource
	private WeChatMessageDao weChatMessageDao;
	
	
	public Integer save(Message weChatMessage)throws Exception{
		 
		 return weChatMessageDao.save(weChatMessage);
	}
	
}
