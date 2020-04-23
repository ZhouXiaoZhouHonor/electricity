package com.ze.zhou.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Area;
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
	public void insertCoordinate() {
		Coordinate c=new Coordinate();
		Area area=new Area();
		area.setAreaId(1);
		c.setArea(area);
		c.setCoordinateEnableStatus(1);
		/* 117.251097,31.865709 *///经纬度
		c.setCoordinateName("安农大");
		c.setCoordinateLongitude(117.251097d);
		c.setCoordinateLatitude(31.865709d);
		c.setPriority(14);
		c.setCoordinatePileNumber(5);
		c.setCreateTime(new Date());
		c.setLastEditTime(new Date());
		int effect=coordinateDao.insertCoordinate(c);
		System.out.println(effect);
	}
	
	@Test
	@Ignore
	public void queryCoordinateTest() {
		List<Coordinate> coordinateList=coordinateDao.queryCoordinate(1);
		for(Coordinate co:coordinateList) {
			System.out.println(co.toString());
		}
	}
}
