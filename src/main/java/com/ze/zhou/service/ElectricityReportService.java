package com.ze.zhou.service;

import com.ze.zhou.dto.ElectricityReportExecution;
import com.ze.zhou.entity.ElectricityReport;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
public interface ElectricityReportService {
	//添加报表数据
	ElectricityReportExecution addReport(ElectricityReport electriciityReport);
	//查询数据，分页查询
	ElectricityReportExecution selectReport(int pageIndex,int pageSize);
}
