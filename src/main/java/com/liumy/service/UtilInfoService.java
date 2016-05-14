package com.liumy.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.liumy.dao.UtilInfoDao;
import com.liumy.entity.UtilInfo;

@Service
@Transactional
public class UtilInfoService {
	
	@Resource
	private UtilInfoDao utilInfoDao;

	public List<UtilInfo> findUtilInfoList(String content)throws Exception{
		
	        return utilInfoDao.findUtilInfoList(content);
		
		} 
	
}
