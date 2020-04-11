package com.ze.zhou.dao;

import java.util.List;

import com.ze.zhou.entity.Coordinate;

/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
public interface CoordinateDao {
	//获取coordinate集合,通过areaId获取指定区域内的coordinate
	List<Coordinate> queryCoordinate(int areaId);
}
