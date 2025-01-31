package com.ze.zhou.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Operator;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
public class AreaDaoTest extends BaseTest{

	@Autowired
	private AreaDao areaDao;
	
	@Test
	@Ignore
	public void updateArea() {
		Area area=new Area();
		Operator operator=new Operator();
		operator.setOperatorId(2);
		area.setOperator(operator);
		area.setAreaId(1);
		area.setAreaEnableStatus(0);
		int effectnum=areaDao.updateArea(area);
		System.out.println(effectnum);
	}
	
	@Test
	@Ignore
	public void queryAreaCount() {
		int count=areaDao.queryAreaCount();
		System.out.println(count);
	}
	
	@Test
	//@Ignore
	public void queryAreaByOperatorAndPageTest() {
		List<Area> areaList=areaDao.queryAreaPage(0, 5);
		System.out.println(areaList.size());
	}
	
	@Test
	@Ignore
	public void deleteAreaTest() {
		int effectnum=areaDao.deleteArea(3);
		assertEquals(1,effectnum);
	}
	
	@Test
	@Ignore
	public void insertAreaTest() {
		Area area=new Area();
		area.setAreaName("瑶海区");
		area.setAreaPriority(3);
		area.setCreateTime(new Date());
		area.setLastEditTime(new Date());
		Operator operator=new Operator();
		operator.setOperatorId(1);
		area.setOperator(operator);
		int effectnum=areaDao.insertArea(area);
		assertEquals(1,effectnum);
	}
	
	@Test
	@Ignore
	public void queryAreaByOperator() {
		Operator op=new Operator();
		op.setOperatorId(1);
		Area area=new Area();
		area.setOperator(op);
		List<Area> areaList=areaDao.queryAreaByOperator(area);
		System.out.println(areaList.get(0).getAreaEnableStatus());
	}
	
	@Test
	@Ignore
	public void queryAreaTest() {
		List<Area> list=areaDao.queryArea();
		//assertEquals(1,list.size());
		System.out.println(list.get(0).getAreaName());
	}
	
}
