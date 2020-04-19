package com.ze.zhou.dao;

import java.util.List;

import com.ze.zhou.entity.Operator;

/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
public interface OperatorDao {
	//获取表中所有的操作员信息
	List<Operator> queryOperator();
}
