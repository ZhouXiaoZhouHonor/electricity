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
import com.ze.zhou.util.ImageHolder;
import com.ze.zhou.util.ImageSize;
import com.ze.zhou.util.ImageUtil;
import com.ze.zhou.util.MD5Salt;
import com.ze.zhou.util.PathUtil;

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
		logger.debug("进入手机账号验证");
		if(phoneUser!=null) {
			PhoneUser pu=phoneUserDao.selectPhoneUserByAccount(phoneUser.getUserAccountNumber());
			logger.debug("是否获取到账号");
			if(pu!=null) {
				Boolean result=MD5Salt.getSaltverifyMD5(
						phoneUser.getUserAccountPassword(),pu.getUserAccountPassword());
				logger.debug("账号验证成功");
				if(result) {//账号密码正确
					logger.debug("开始更新状态");
					//更新用户状态，更新为在线状态
					pu.setUserOnline(1);
					int result1=phoneUserDao.updatePhoneUser(pu);
					if(result1>0) {
						logger.debug("账号验证成功，此账号为在线状态");
						//将不必显示的内容全部置空
						pu.setUserAccountPassword(null);
						pu.setUserId(null);
						pue.setPhoneUser(pu);
						pue.setState(PhoneUserStateEnum.SUCCESS.getState());
					}else {
						pue.setState(PhoneUserStateEnum.OFFLINE.getState());
					}
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

	@Override
	public PhoneUserExecution changePhoneUser(PhoneUser phoneUser,ImageHolder imageHolder) {
		PhoneUserExecution pue=new PhoneUserExecution();
		logger.debug("s进入service层");
		if(phoneUser!=null&&phoneUser.getUserAccountNumber()!=null) {
			//向数据库查找该账号的Id
			logger.debug("s对象参数都有");
			PhoneUser phoneUserReal=phoneUserDao.selectPhoneUserByAccount(
					phoneUser.getUserAccountNumber());
			if(phoneUserReal!=null) {//查找到该账号信息
				logger.debug("s查找到对应账号");
				String imgPath="";
				//判断是否需要更新图片
				if(imageHolder!=null) {
					logger.debug("s需要更改图片");
					String dest=PathUtil.getPhoneUserImagePath(
							phoneUserReal.getUserId());
					imgPath=ImageUtil.generateThumbnail(
							imageHolder, dest, ImageSize.IMAGE_USER);
					phoneUser.setUserImg(imgPath);
					logger.debug("s更改图片成功");
				}
				logger.debug("s即将更新操作");
				phoneUser.setUserId(phoneUserReal.getUserId());
				phoneUser.setLastEditTime(new Date());
				int effectNum=phoneUserDao.updatePhoneUser(phoneUser);
				if(effectNum>0) {
					logger.debug("s更新成功");
					pue.setState(PhoneUserStateEnum.SUCCESS.getState());
					//将更新完的新的数据一并返回
					pue.setPhoneUser(phoneUserDao.selectPhoneUserByAccount(
							phoneUser.getUserAccountNumber()));
				}else {
					logger.debug("s更新失败");
					pue.setState(PhoneUserStateEnum.FAILURE.getState());
				}
			}else {
				logger.debug("s没有找到对应的账号");
				pue.setState(PhoneUserStateEnum.NULL_PHONEUSER.getState());
			}
		}else {
			logger.debug("s参数为空");
			pue.setState(PhoneUserStateEnum.NULL_PHONEUSERID.getState());
		}
		return pue;
	}

	@Override
	public PhoneUserExecution getPhoneUser(String phoneUserAccount) {
		PhoneUserExecution pue=new PhoneUserExecution();
		if(phoneUserAccount!=null&&!("".equals(phoneUserAccount))) {
			PhoneUser result=phoneUserDao.selectPhoneUserByAccount(phoneUserAccount);
			if(result!=null) {
				pue.setState(PhoneUserStateEnum.SUCCESS.getState());
				pue.setPhoneUser(result);
			}else {
				pue.setState(PhoneUserStateEnum.FAILURE.getState());
			}
		}else {
			pue.setState(PhoneUserStateEnum.NULL_PHONEUSERID.getState());
		}
		return pue;
	}

	@Override
	public PhoneUserExecution getCountUser(int userOnline) {
		PhoneUserExecution pue=new PhoneUserExecution();
		int result=phoneUserDao.countUserOnline(userOnline);
		if(result>=0) {//有可能不在线，人数为0的情况
			pue.setState(PhoneUserStateEnum.SUCCESS.getState());
			pue.setCount(result);
		}else {
			pue.setState(PhoneUserStateEnum.FAILURE.getState());
		}
		return pue;
	}
}
