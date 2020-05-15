package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.dto.OperatorExecution;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.util.ImageHolder;


/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
public interface OperatorService {
	//获取全部的operator
	List<Operator> getQueryOperator();
	
	//验证登陆账号是否正确并返回结果
	OperatorExecution operatorCheck(Operator operator);
	
	//添加管理员
	OperatorExecution addOperator(Operator operator,ImageHolder imageHolder);
	
	//更新管理员状态
	OperatorExecution changeOperator(Operator operator);
	
	//分页获取operator
	OperatorExecution getQueryOperatorByPage(int pageIndex,int pageSize);
}
