package com.ze.zhou.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Operator;

/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
public class OperatorDaoTest extends BaseTest{
	@Autowired
	private OperatorDao operatorDao;
	
	@Test
	//@Ignore
	public void queryOperatorTest() {
		List<Operator> operatorList=operatorDao.queryOperator();
		System.out.println(operatorList.size());
	}
}








