package com.ze.zhou.service.impl;

import java.util.Date;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.PhoneUserDao;
import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.enums.PhoneUserStateEnum;
import com.ze.zhou.service.PhoneUserService;
import com.ze.zhou.util.MD5Salt;
import com.ze.zhou.web.phoneadmin.PhoneController;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
@Service
public class PhoneUserServiceImpl implements PhoneUserService{

	Logger logger=(Logger) LoggerFactory.getLogger(PhoneUserServiceImpl.class);
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
					//将不必显示的内容全部置空
					pu.setUserAccountPassword(null);
					pu.setUserId(null);
					pue.setPhoneUser(pu);
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

	/* @SuppressWarnings("unused") */
	@SuppressWarnings("unused")
	@Override
	public PhoneUserExecution checkPhoneUserAccount(String phoneUserAccount) {
		PhoneUserExecution pue=new PhoneUserExecution();
		if(phoneUserAccount!=null&&!("".equals(phoneUserAccount))) {
			PhoneUser phoneUser=phoneUserDao.selectPhoneUserByAccount(phoneUserAccount);
			logger.debug("账号:"+phoneUser.getUserAccountNumber());
			if(phoneUser!=null) {
				logger.debug("phoneUser不为空");
				pue.setState(PhoneUserStateEnum.SUCCESS.getState());
			}else {
				logger.debug("phoneUser为空");
				pue.setState(PhoneUserStateEnum.FAILURE.getState());
			}
		}else {
			pue.setState(PhoneUserStateEnum.NULL_PHONEUSERID.getState());
		}
		return pue;
	}

	@Override
	public PhoneUserExecution addPhoneUser(PhoneUser phoneUser) {
		PhoneUserExecution pue=new PhoneUserExecution();
		if(phoneUser!=null&&phoneUser.getUserAccountNumber()!=null&&
				phoneUser.getUserAccountPassword()!=null) {
			phoneUser.setCreateTime(new Date());
			phoneUser.setLastEditTime(new Date());
			phoneUser.setUserName("用户名");
			logger.debug("添加默认属性成功");
			int effectNum=phoneUserDao.insertPhoneUser(phoneUser);
			if(effectNum>0) {//添加成功
				logger.debug("添加成功");
				pue.setState(PhoneUserStateEnum.SUCCESS.getState());
			}else {
				logger.debug("添加失败");
				pue.setState(PhoneUserStateEnum.FAILURE.getState());
			}
		}else {
			logger.debug("对象为空");
			pue.setState(PhoneUserStateEnum.NULL_PHONEUSER.getState());
		}
		return pue;
	}
}
