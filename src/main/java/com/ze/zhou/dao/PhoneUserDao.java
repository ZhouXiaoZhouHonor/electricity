package com.ze.zhou.dao;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.PhoneUser;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
public interface PhoneUserDao {
	//获取所有手机用户信息,分页查找
	
	//根据手机用户的账号查找用户
	PhoneUser selectPhoneUserByAccount(@Param("phoneUserAccount")String phoneUserAccount);
	//添加手机用户
	int insertPhoneUser(PhoneUser phoneUser);
	//更新手机用户状态
	int updatePhoneUser(PhoneUser phoneUser);
	//统计用户在线人数
	int countUserOnline();
}
