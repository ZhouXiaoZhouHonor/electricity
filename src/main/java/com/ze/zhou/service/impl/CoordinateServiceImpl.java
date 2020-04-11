package com.ze.zhou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.CoordinateDao;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.service.CoordinateService;


/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
@Service
public class CoordinateServiceImpl implements CoordinateService{

	@Autowired
	private CoordinateDao coordinateDao;
	/*
	 * @Param areaId
	 * @Return List<Coordinate>*/
	@Override
	public List<Coordinate> getCoordinateList(int areaId) {
		// TODO Auto-generated method stub
		return coordinateDao.queryCoordinate(areaId);
	}

}
