package com.ze.zhou.service.impl;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.OperatorDao;
import com.ze.zhou.dto.OperatorExecution;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.enums.OperatorStateEnum;
import com.ze.zhou.service.OperatorService;
import com.ze.zhou.util.MD5Salt;
import com.ze.zhou.web.pageadmin.LoginController;

import ch.qos.logback.classic.Logger;
/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
@Service
public class OperatorServiceImpl implements OperatorService{

	Logger logger=(Logger) LoggerFactory.getLogger(OperatorServiceImpl.class);
	
	@Autowired
	private OperatorDao operatorDao;
	
	//获取所有的操作员
	@Override
	public List<Operator> getQueryOperator() {
		return operatorDao.queryOperator();
	}
	
	//验证登陆账号是否正确并返回结果
	@Override
	public OperatorExecution operatorCheck(Operator operator) {
		OperatorExecution oe=new OperatorExecution();
		if(operator!=null) {
			//获取对应的账户号密码
			Operator operatorReal=operatorDao.selectOperatorByNumber(operator.getOperatorAccountNumber());
			if(operatorReal!=null) {
				//验证密码
				Boolean result=MD5Salt.getSaltverifyMD5(operator.getOperatorPassword(),
						operatorReal.getOperatorPassword());
				logger.debug("密码验证结果:"+result);
				if(result) {
					oe.setState(OperatorStateEnum.CHECK_SUCCESS.getState());
					oe.setOperator(operatorReal);
				}else {
					oe.setState(OperatorStateEnum.CHECK_FAILURE.getState());
				}
			}else {
				oe.setState(OperatorStateEnum.NULL_OPERATOR.getState());
			}
		}else {
			oe.setState(OperatorStateEnum.NULL_OPERATOR.getState());
		}
		return oe;
	}
}
