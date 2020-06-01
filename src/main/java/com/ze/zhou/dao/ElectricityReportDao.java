package com.ze.zhou.dao;

import java.util.List;

import com.ze.zhou.entity.ElectricityReport;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
public interface ElectricityReportDao {
	//添加报表信息
	int insertReport();
	//获取某充电桩的所有报表信息
	List<ElectricityReport> queryReportById();
	
}
