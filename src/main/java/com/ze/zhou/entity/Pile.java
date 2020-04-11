package com.ze.zhou.entity;

import java.util.Date;



/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
public class Pile {
	private Long pileId;
	private String pileName;//由coordinateName+pileNumber组合而成(肯定是唯一的)
	private Double pileLongitude;//经度
	private Double pileLatitude;//纬度
	private String pileImg;//充电桩缩略图
	private Integer pileEnableStatus;//0、审核未通过；1、审核已通过
	private Date createTime;//创建时间
	private Date lastEditTime;//更新时间，最近的一次更新时间
	private String pileAddr;//详细地址
	private String pileDesc;//详细介绍
	private Area area;//区域信息
	private Operator operator;//管理员信息
	private Coordinate coordinate;//坐标位置(一个集中的参考点)
	private Integer pileNumber;//桩号
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	/*To DO
	 * 该充电桩的每条数据。*/
	public Long getPileId() {
		return pileId;
	}
	public void setPileId(Long pileId) {
		this.pileId = pileId;
	}
	public String getPileName() {
		return pileName;
	}
	public void setPileName(String pileName) {
		this.pileName = pileName;
	}
	public Double getPileLongitude() {
		return pileLongitude;
	}
	public void setPileLongitude(Double pileLongitude) {
		this.pileLongitude = pileLongitude;
	}
	public Double getPileLatitude() {
		return pileLatitude;
	}
	public void setPileLatitude(Double pileLatitude) {
		this.pileLatitude = pileLatitude;
	}
	public String getPileImg() {
		return pileImg;
	}
	public void setPileImg(String pileImg) {
		this.pileImg = pileImg;
	}
	public Integer getPileEnableStatus() {
		return pileEnableStatus;
	}
	public void setPileEnableStatus(Integer pileEnableStatus) {
		this.pileEnableStatus = pileEnableStatus;
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
	public String getPileAddr() {
		return pileAddr;
	}
	public void setPileAddr(String pileAddr) {
		this.pileAddr = pileAddr;
	}
	public String getPileDesc() {
		return pileDesc;
	}
	public void setPileDesc(String pileDesc) {
		this.pileDesc = pileDesc;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Integer getPileNumber() {
		return pileNumber;
	}
	public void setPileNumber(Integer pileNumber) {
		this.pileNumber = pileNumber;
	}
	
	
}
