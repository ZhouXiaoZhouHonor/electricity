package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年4月3日
	@goal:
*/
public class Coordinate {
	private Integer coordinateId;//id
	private Double coordinateLongitude;//坐标经度
	private Double coordinateLatitude;//坐标纬度
	private Date createTime;//创建时间
	private Date lastEditTime;//修改时间
	private Area area;//通过区域信息来获得该区域下的充电桩的坐标信息
	private Integer priority;//优先级
	private String coordinateName;
	private Integer coordinatePileNumber;//存放充电桩的数量
	private Integer coordinateEnableStatus;//站点是否可用
	public Integer getCoordinateId() {
		return coordinateId;
	}
	public void setCoordinateId(Integer coordinateId) {
		this.coordinateId = coordinateId;
	}
	
	public Double getCoordinateLongitude() {
		return coordinateLongitude;
	}
	public void setCoordinateLongitude(Double coordinateLongitude) {
		this.coordinateLongitude = coordinateLongitude;
	}
	public Double getCoordinateLatitude() {
		return coordinateLatitude;
	}
	public void setCoordinateLatitude(Double coordinateLatitude) {
		this.coordinateLatitude = coordinateLatitude;
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
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getCoordinateName() {
		return coordinateName;
	}
	public void setCoordinateName(String coordinateName) {
		this.coordinateName = coordinateName;
	}
	public Integer getCoordinatePileNumber() {
		return coordinatePileNumber;
	}
	public void setCoordinatePileNumber(Integer coordinatePileNumber) {
		this.coordinatePileNumber = coordinatePileNumber;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getCoordinateEnableStatus() {
		return coordinateEnableStatus;
	}
	public void setCoordinateEnableStatus(Integer coordinateEnableStatus) {
		this.coordinateEnableStatus = coordinateEnableStatus;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+coordinateId+"/"+coordinateLongitude+"/"+coordinateLatitude
				+"/"+createTime+"/"+lastEditTime+"/"+area.getAreaId()+"/"
		+coordinateName+"/"+coordinatePileNumber+"}";
	}
	
}
