package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.dto.CoordinateExecution;
import com.ze.zhou.entity.Coordinate;

/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
public interface CoordinateService {
	//通过areaId获取对应的coordinate集合对象
	List<Coordinate> getCoordinateList(int areaId);
	//添加站点信息
	CoordinateExecution addCoordinate(Coordinate coordinate);
	//更新站点信息
	CoordinateExecution changeCoordinate(Coordinate coordinate);
	//分页获取数据
	CoordinateExecution getCoordinateList(int pageIndex,int pageSize);
	//手机用户获取站点数据
	List<Coordinate> getAllCoordinateList();
	//获取对应Id的coordinate
	CoordinateExecution getCoordinate(int coordinateId);
}
