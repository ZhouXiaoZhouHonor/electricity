package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.entity.Operator;

/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
public interface OperatorService {
	//获取全部的operator
	List<Operator> getQueryOperator();
}
