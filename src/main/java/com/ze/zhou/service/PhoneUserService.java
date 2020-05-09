package com.ze.zhou.service;

import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.entity.PhoneUser;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
public interface PhoneUserService {
	//验证登录账号、密码是否正确
	PhoneUserExecution checkPhoneUser(PhoneUser phoneUser);
}
