package com.ze.zhou.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Area;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
public class AreaDaoTest extends BaseTest{

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void queryAreaTest() {
		List<Area> list=areaDao.queryArea();
		//assertEquals(1,list.size());
		System.out.println(list.get(0).getAreaName());
	}
	
}
