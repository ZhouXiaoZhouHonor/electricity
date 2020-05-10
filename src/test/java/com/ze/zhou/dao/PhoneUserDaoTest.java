package com.ze.zhou.dao;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.PhoneUser;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
public class PhoneUserDaoTest extends BaseTest{
	@Autowired
	private PhoneUserDao phoneUserDao;
	
	@Test
	public void insertPhoneUser() {
		PhoneUser phoneUser=new PhoneUser();
		phoneUser.setCreateTime(new Date());
		phoneUser.setLastEditTime(new Date());
		phoneUser.setUserAccountNumber("13865066757");
		phoneUser.setUserAccountPassword("123456");
		phoneUser.setUserName("周周");
		int effectNum=phoneUserDao.insertPhoneUser(phoneUser);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void selectPhoneUserByAccount() {
		PhoneUser phoneUser=phoneUserDao.selectPhoneUserByAccount("15256038842");
		System.out.println(phoneUser.getUserAccountNumber());
	}
}




