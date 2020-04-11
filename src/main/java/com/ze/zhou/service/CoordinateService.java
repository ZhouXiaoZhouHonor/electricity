package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.entity.Coordinate;

/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
public interface CoordinateService {
	//通过areaId获取对应的coordinate集合对象
	List<Coordinate> getCoordinateList(int areaId);
}
