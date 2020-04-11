package com.ze.zhou.service;

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
public class AreaServiceImplTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	
	@Test
	public void getQueryAreaTest() {
		List<Area> list=areaService.getQueryArea();
		assertEquals(1,list.size());
	}
}
