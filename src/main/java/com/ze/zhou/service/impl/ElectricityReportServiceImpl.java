package com.ze.zhou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.ElectricityReportDao;
import com.ze.zhou.dto.ElectricityReportExecution;
import com.ze.zhou.entity.ElectricityReport;
import com.ze.zhou.enums.ElectricityReportStateEnum;
import com.ze.zhou.service.ElectricityReportService;
import com.ze.zhou.util.PageCalculator;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
@Service
public class ElectricityReportServiceImpl implements ElectricityReportService{
	@Autowired
	private ElectricityReportDao electricityReportDao;
	
	//添加报表数据到数据库中
	@Override
	public ElectricityReportExecution addReport(
			ElectricityReport electricityReport) {
		ElectricityReportExecution ere=new ElectricityReportExecution();
		if(electricityReport!=null&&electricityReport.getPile().getPileId()>0) {
			electricityReport.setCreateTime(new Date());
			electricityReport.setElectricityReportEnableStatus(1);
			int result=electricityReportDao.insertReport(electricityReport);
			if(result>0) {
				ere.setState(ElectricityReportStateEnum.SUCCESS.getState());
			}else {
				ere.setState(ElectricityReportStateEnum.FAILURE.getState());
			}
		}else {
			ere.setState(ElectricityReportStateEnum.NULL_ELECTRICITYREPORT.getState());
		}
		return ere;
	}

	@Override
	public ElectricityReportExecution selectReport(int pageIndex, int pageSize) {
		ElectricityReportExecution ere=new ElectricityReportExecution();
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<ElectricityReport> list=electricityReportDao.queryReport(rowIndex, pageSize);
		int count=electricityReportDao.queryReportCount();
		if(list!=null&&list.size()>0&&count>0) {
			ere.setCount(count);
			ere.setElectricityReportList(list);
			ere.setState(ElectricityReportStateEnum.SUCCESS.getState());
		}else {
			ere.setState(ElectricityReportStateEnum.NULL_ELECTRICITYREPORT.getState());
		}
		return ere;
	}

}
