package com.ze.zhou.dao;

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
	@Ignore
	public void selectPhoneUserByAccount() {
		PhoneUser phoneUser=phoneUserDao.selectPhoneUserByAccount("15256038842");
		System.out.println(phoneUser.getUserAccountNumber());
	}
}




