package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.Coordinate;

/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
public interface CoordinateDao {
	//获取coordinate集合,通过areaId获取指定区域内的coordinate
	List<Coordinate> queryCoordinate(int areaId);
	//添加站点信息
	int insertCoordinate(Coordinate coordinate);
	//更新站点
	int updateCoordinate(Coordinate coordinate);
	//分页获取数据
	int queryCountCoordinate();//获取总数
	//按分页获取数据
	List<Coordinate> queryCoordinateList(@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
}
