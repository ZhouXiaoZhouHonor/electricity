package com.ze.zhou.dao;

import static org.junit.Assert.assertEquals;

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
	public void queryAreaByOperator() {
		Operator op=new Operator();
		op.setOperatorId(1);
		Area area=new Area();
		area.setOperator(op);
		List<Area> areaList=areaDao.queryAreaByOperator(area);
		System.out.println(areaList.size());
	}
	
	@Test
	@Ignore
	public void queryAreaTest() {
		List<Area> list=areaDao.queryArea();
		//assertEquals(1,list.size());
		System.out.println(list.get(0).getAreaName());
	}
	
}
