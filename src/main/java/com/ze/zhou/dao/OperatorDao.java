package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.Operator;

/*
	author:zhouze
	@createTime:2020年4月19日
	@goal:
*/
public interface OperatorDao {
	//获取表中所有的操作员信息
	List<Operator> queryOperator();
	//通过账号查找操作员
	Operator selectOperatorByNumber(@Param("operatorAccountNumber")
		String operatorAccountNumber);
	//添加管理员
	int insertOperator(Operator operator);
	//更新管理员
	int updateOperator(Operator operator);
	//通过分页获取operator
	List<Operator> queryOperatorByPage(@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	//获取分页的总数
	int queryCount();
}
