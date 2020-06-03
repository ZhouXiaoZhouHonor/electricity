package com.ze.zhou.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.ElectricityReport;
import com.ze.zhou.entity.Pile;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
public class ElectricityReportDaoTest extends BaseTest{
	@Autowired
	private ElectricityReportDao electricityReportDao;
	
	@Test
	@Ignore
	public void queryReport() {
		List<ElectricityReport> list=electricityReportDao.queryReport(0,5);
		System.out.println(list.size());
		System.out.println(list.get(0).getPile().getPileName());
		int count=electricityReportDao.queryReportCount();
		System.out.println(count);
	}
	
	@Test
	@Ignore
	public void insertElectrityReportTest() {
		Pile pile=new Pile();
		pile.setPileId(23L);
		ElectricityReport el=new ElectricityReport();
		el.setCreateTime(new Date());
		el.setElectricityReportEnableStatus(1);
		el.setElectricityReportLink("test");
		el.setPile(pile);
		el.setElectricityReportName("Test");
		int result=electricityReportDao.insertReport(el);
		System.out.println(result);
	}
}
