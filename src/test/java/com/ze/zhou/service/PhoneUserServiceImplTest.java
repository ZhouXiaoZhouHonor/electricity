package com.ze.zhou.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.util.ImageHolder;

/*
	author:zhouze
	@createTime:2020年5月12日
	@goal:
*/
public class PhoneUserServiceImplTest extends BaseTest{
	@Autowired
	private PhoneUserService phoneUserService;
	
	@Test
	@Ignore
	public void updatePhoneUser() {
		InputStream is = null;
		try {
			is=new FileInputStream("E:/Images/yellow.jpg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageHolder ih=new ImageHolder();
		ih.setImage(is);
		ih.setImageName("yellow.jpg");
		PhoneUser pu=new PhoneUser();
		pu.setLastEditTime(new Date());
		pu.setUserAccountNumber("13865066757");
		PhoneUserExecution pue=phoneUserService.changePhoneUser(pu, ih);
		System.out.println(pue.getState());
	}
}





