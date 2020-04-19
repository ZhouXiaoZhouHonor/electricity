package com.ze.zhou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.OperatorDao;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.service.OperatorService;
/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
@Service
public class OperatorServiceImpl implements OperatorService{

	@Autowired
	private OperatorDao operatorDao;
	
	//获取所有的操作员
	@Override
	public List<Operator> getQueryOperator() {
		return operatorDao.queryOperator();
	}

}
