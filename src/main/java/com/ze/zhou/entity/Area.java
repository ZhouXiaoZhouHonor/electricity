package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
public class Area {
	private Integer areaId;
	private String areaName;
	private Integer areaPriority;
	private Date createTime;
	private Date lastEditTime;
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getAreaPriority() {
		return areaPriority;
	}
	public void setAreaPriority(Integer areaPriority) {
		this.areaPriority = areaPriority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
	
}
