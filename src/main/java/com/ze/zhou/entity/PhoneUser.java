package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public class PhoneUser {
	private Integer userId;
	private String userName;
	private String userAccountNumber;
	private String userAccountPassword;
	private String userImg;
	private Date createTime;
	private Date lastEditTime;
	private Integer userOnline;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccountNumber() {
		return userAccountNumber;
	}
	public void setUserAccountNumber(String userAccountNumber) {
		this.userAccountNumber = userAccountNumber;
	}
	public String getUserAccountPassword() {
		return userAccountPassword;
	}
	public void setUserAccountPassword(String userAccountPassword) {
		this.userAccountPassword = userAccountPassword;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
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
	public Integer getUserOnline() {
		return userOnline;
	}
	public void setUserOnline(Integer userOnline) {
		this.userOnline = userOnline;
	}
}
