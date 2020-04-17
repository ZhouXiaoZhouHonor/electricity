package com.ze.zhou.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.PileElectricity;

/*
	author:zhouze
	@createTime:2020年4月15日
	@goal:
*/
public class PileElectricityDaoTest extends BaseTest{
	@Autowired
	private PileElectricityDao pileElectricityDao;
	
	@Test
	//@Ignore
	public void queryPileElectricityListByPileIdAndDateTest() {
		Date firstTime = null;
		Date endTime = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			firstTime=sdf.parse("2020-04-15 22:06:18");
			endTime=sdf.parse("2020-04-15 23:06:18"); 
		}catch(Exception e) {
			e.printStackTrace(); 
		}
		System.out.println(firstTime==null);
		System.out.println(endTime==null);
		System.out.println(firstTime.getTime());
		System.out.println(endTime.getTime());
		List<PileElectricity> pe=pileElectricityDao.queryPileElectricityListByPileIdAndDate(23, firstTime, endTime);
		System.out.println(pe.size());
	}
}








