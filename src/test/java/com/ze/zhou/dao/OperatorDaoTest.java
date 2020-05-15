package com.ze.zhou.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.util.MD5Salt;

/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
public class OperatorDaoTest extends BaseTest{
	@Autowired
	private OperatorDao operatorDao;
	
	@Test
	public void queryOperatorByPageTest() {
		List<Operator> list=operatorDao.queryOperatorByPage(0, 5);
		int count=operatorDao.queryCount();
		System.out.println(list.get(0).getOperatorAccountNumber());
		System.out.println(list.size());
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void updateOperator() {
		Operator operator=new Operator();
		operator.setOperatorEnableStatus(1);
		operator.setOperatorId(3);
		operator.setLastEditTime(new Date());
		int effectNum=operatorDao.updateOperator(operator);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void insertOperatorTest() {
		Operator operator=new Operator();
		operator.setCreateTime(new Date());
		operator.setLastEditTime(new Date());
		operator.setOperatorAccountNumber("147");
		operator.setOperatorEnableStatus(1);
		operator.setOperatorImg("tupian");
		operator.setOperatorName("胡承进");
		operator.setOperatorPassword(MD5Salt.getSaltMD5("147"));
		int effectNum=operatorDao.insertOperator(operator);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void selectOperatorByNumber() {
		/*
		 * Operator operator=new Operator(); operator.setOperatorAccountNumber("110");
		 */
		Operator op=operatorDao.selectOperatorByNumber("110");
		System.out.println(op.toString());
	}
	
	@Test
	@Ignore
	public void queryOperatorTest() {
		List<Operator> operatorList=operatorDao.queryOperator();
		System.out.println(operatorList.size());
	}
}
