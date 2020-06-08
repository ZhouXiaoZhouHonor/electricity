package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/

public class Problem {
	private Integer problemId;
	private String problemDesc;
	private String problemTitle;
	private Integer problemEnableStatus;
	private Date createTime;
	private Date lastEditTime;
	private PhoneUser user;
	private String problemSalve;
	
	public String getProblemSalve() {
		return problemSalve;
	}
	public void setProblemSalve(String problemSalve) {
		this.problemSalve = problemSalve;
	}
	public Integer getProblemId() {
		return problemId;
	}
	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}
	public String getProblemDesc() {
		return problemDesc;
	}
	public void setProblemDesc(String problemDesc) {
		this.problemDesc = problemDesc;
	}
	public String getProblemTitle() {
		return problemTitle;
	}
	public void setProblemTitle(String problemTitle) {
		this.problemTitle = problemTitle;
	}
	public Integer getProblemEnableStatus() {
		return problemEnableStatus;
	}
	public void setProblemEnableStatus(Integer problemEnableStatus) {
		this.problemEnableStatus = problemEnableStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PhoneUser getUser() {
		return user;
	}
	public void setUser(PhoneUser user) {
		this.user = user;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
}
