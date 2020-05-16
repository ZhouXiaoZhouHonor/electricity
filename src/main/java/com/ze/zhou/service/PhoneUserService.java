package com.ze.zhou.service;

import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.util.ImageHolder;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
public interface PhoneUserService {
	//验证登录账号、密码是否正确,登录系统过程中
	PhoneUserExecution checkPhoneUser(PhoneUser phoneUser);
	
	//验证注册的账号是否存在
	PhoneUserExecution checkPhoneUserAccount(String phoneUserAccount);
	
	//添加手机用户
	PhoneUserExecution addPhoneUser(PhoneUser phoneUser);
	
	//更新手机用户
	PhoneUserExecution changePhoneUser(PhoneUser phoneUser,ImageHolder imageHolder);
	
	//获取对应账号的对象
	PhoneUserExecution getPhoneUser(String phoneUserAccount);
	
}
