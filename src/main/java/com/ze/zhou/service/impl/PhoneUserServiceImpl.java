package com.ze.zhou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.PhoneUserDao;
import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.enums.PhoneUserStateEnum;
import com.ze.zhou.service.PhoneUserService;
import com.ze.zhou.util.MD5Salt;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
@Service
public class PhoneUserServiceImpl implements PhoneUserService{

	@Autowired
	private PhoneUserDao phoneUserDao;
	
	//验证账号是否正确
	@Override
	public PhoneUserExecution checkPhoneUser(PhoneUser phoneUser) {
		PhoneUserExecution pue=new PhoneUserExecution();
		if(phoneUser!=null) {
			PhoneUser pu=phoneUserDao.selectPhoneUserByAccount(phoneUser.getUserAccountNumber());
			if(pu!=null) {
				Boolean result=MD5Salt.getSaltverifyMD5(
						phoneUser.getUserAccountPassword(),pu.getUserAccountPassword());
				if(result) {//账号密码正确
					pue.setState(PhoneUserStateEnum.SUCCESS.getState());
				}else {//账号密码错误
					pue.setState(PhoneUserStateEnum.FAILURE.getState());
				}
			}else {//未查询到对应的账号
				pue.setState(PhoneUserStateEnum.NULL_PHONEUSER.getState());
			}
		}else {//参数为null
			pue.setState(PhoneUserStateEnum.NULL_PHONEUSER.getState());
		}
		return pue;
	}

}
