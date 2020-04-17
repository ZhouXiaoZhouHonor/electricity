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
	
	//添加区域信息
	int insertArea(Area area);
	
	//删除区域信息，根据areaId进行删除
	int deleteArea(int areaId);
}
