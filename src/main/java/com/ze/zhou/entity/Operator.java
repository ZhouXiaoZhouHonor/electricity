package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年4月3日
	@goal:
*/
public class Operator {
	private Integer operatorId;//id
	private String operatorName;//管理员名称
	private String operatorImg;//管理员头像
	private String operatorAccountNumber;////管理员登陆账号
	private String operatorPassword;//管理员登陆密码
	private Integer operatorEnableStatus;//管理员账户号是否可用，1、可用；0、不可用
	private Date createTime;//管理员创建时间
	private Date lastEditTime;//管理员修改时间
	
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorImg() {
		return operatorImg;
	}
	public void setOperatorImg(String operatorImg) {
		this.operatorImg = operatorImg;
	}
	public String getOperatorAccountNumber() {
		return operatorAccountNumber;
	}
	public void setOperatorAccountNumber(String operatorAccountNumber) {
		this.operatorAccountNumber = operatorAccountNumber;
	}
	public String getOperatorPassword() {
		return operatorPassword;
	}
	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
	}
	public Integer getOperatorEnableStatus() {
		return operatorEnableStatus;
	}
	public void setOperatorEnableStatus(Integer operatorEnableStatus) {
		this.operatorEnableStatus = operatorEnableStatus;
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
