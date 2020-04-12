package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.Area;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
public interface AreaDao {
	//查询所有区域信息
	List<Area> queryArea();
	
	//根据OperatorId将对应的area取出来
	List<Area> queryAreaByOperator(@Param("areaCondition")Area areaCondition);
}
