package com.ze.zhou.dao;

import java.util.Date;

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
	//@Ignore
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
