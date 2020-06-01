package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年6月1日
	@goal:
*/
public class ElectricityReport {
	private Long electricityReportId;
	private String electricityReportName;
	private String electricityReportLink;
	private int electricityReportEnableStatus;
	private Date createTime;
	private Pile pile;
	
	public Long getElectricityReportId() {
		return electricityReportId;
	}
	public void setElectricityReportId(Long electricityReportId) {
		this.electricityReportId = electricityReportId;
	}
	public String getElectricityReportName() {
		return electricityReportName;
	}
	public void setElectricityReportName(String electricityReportName) {
		this.electricityReportName = electricityReportName;
	}
	public String getElectricityReportLink() {
		return electricityReportLink;
	}
	public void setElectricityReportLink(String electricityReportLink) {
		this.electricityReportLink = electricityReportLink;
	}
	public int getElectricityReportEnableStatus() {
		return electricityReportEnableStatus;
	}
	public void setElectricityReportEnableStatus(int electricityReportEnableStatus) {
		this.electricityReportEnableStatus = electricityReportEnableStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Pile getPile() {
		return pile;
	}
	public void setPile(Pile pile) {
		this.pile = pile;
	}
	
}
