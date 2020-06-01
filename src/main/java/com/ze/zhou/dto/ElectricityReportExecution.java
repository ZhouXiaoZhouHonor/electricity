package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.ElectricityReport;
import com.ze.zhou.enums.AreaStateEnum;
import com.ze.zhou.enums.ElectricityReportStateEnum;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
public class ElectricityReportExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Shop(增删改店铺的时候用到)
	private ElectricityReport electricityReport;
	//shop列表(查找店铺的时候用到)
	private List<ElectricityReport> electricityReportList;
	
	//无参构造方法
	public ElectricityReportExecution() {
		
	}
	
	//带有枚举类型参数的构造方法
	//主要用于店铺操作失败的情况下
	public ElectricityReportExecution(ElectricityReportStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	//成功的构造器
	//返回插入店铺的数据
	public ElectricityReportExecution(ElectricityReportStateEnum stateEnum,ElectricityReport electricityReport) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.electricityReport=electricityReport;
	}
	
	//成功的构造器
	//返回一个店铺对象列表
	public ElectricityReportExecution(ElectricityReportStateEnum stateEnum,List<ElectricityReport> electricityReportList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.electricityReportList=electricityReportList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ElectricityReport getElectricityReport() {
		return electricityReport;
	}

	public void setElectricityReport(ElectricityReport electricityReport) {
		this.electricityReport = electricityReport;
	}

	public List<ElectricityReport> getElectricityReportList() {
		return electricityReportList;
	}

	public void setElectricityReportList(List<ElectricityReport> electricityReportList) {
		this.electricityReportList = electricityReportList;
	}

}
