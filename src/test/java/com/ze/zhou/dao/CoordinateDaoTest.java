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
	@Ignore
	public void queryAllTest() {
		List<Coordinate> coordinateList=coordinateDao.queryAllCoordinate();
		System.out.println(coordinateList.size());
	}
	
	@Test
	@Ignore
	public void queryCoordinateListTest() {
		List<Coordinate> coordinateList=coordinateDao.queryCoordinateList(0, 5);
		System.out.println(coordinateList.size());
		System.out.println(coordinateList.get(0).getCoordinateName());
		System.out.println(coordinateList.get(0).getArea().getAreaName());
	}
	
	@Test
	@Ignore
	public void queryCountCoordinateTest() {
		int num=coordinateDao.queryCountCoordinate();
		System.out.println(num);
	}
	
	@Test
	@Ignore
	public void updateCoordinate() {
		Coordinate c=new Coordinate();
		c.setCoordinateId(4);
		c.setLastEditTime(new Date());
		c.setCoordinatePileNumber(16);
		c.setCoordinateEnableStatus(1);
		int effect=coordinateDao.updateCoordinate(c);
		System.out.println(effect);
	}
	
	@Test
	@Ignore
	public void insertCoordinate() {
		Coordinate c=new Coordinate();
		Area area=new Area();
		area.setAreaId(1);
		c.setArea(area);
		c.setCoordinateEnableStatus(1);
		/* 117.251097,31.865709 */
		//经纬度
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
