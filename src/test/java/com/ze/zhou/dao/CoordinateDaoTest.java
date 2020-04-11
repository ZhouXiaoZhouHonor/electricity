package com.ze.zhou.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Coordinate;

/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
public class CoordinateDaoTest extends BaseTest{
	@Autowired
	private CoordinateDao coordinateDao;
	
	
	
	@Test
	@Ignore
	public void queryCoordinateTest() {
		List<Coordinate> coordinateList=coordinateDao.queryCoordinate(1);
		for(Coordinate co:coordinateList) {
			System.out.println(co.toString());
		}
	}
}
