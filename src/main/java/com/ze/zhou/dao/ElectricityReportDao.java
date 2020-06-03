package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.ElectricityReport;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
public interface ElectricityReportDao {
	//添加报表信息
	int insertReport(ElectricityReport electricityReport);
	//获取某充电桩的所有报表信息
	List<ElectricityReport> queryReport(@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	//获取分页所需要的报表总数
	int queryReportCount();
}
