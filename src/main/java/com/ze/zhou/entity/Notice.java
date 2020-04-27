package com.ze.zhou.entity;

import java.util.Date;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public class Notice {
	private Integer noticeId;
	private String noticeName;
	private Integer noticePriority;
	private String noticeLink;
	private Date createTime;
	private Integer noticeEnableStatus;
	private String noticeImg;
	private Date lastEditTime;
	
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public Integer getNoticePriority() {
		return noticePriority;
	}
	public void setNoticePriority(Integer noticePriority) {
		this.noticePriority = noticePriority;
	}
	public String getNoticeLink() {
		return noticeLink;
	}
	public void setNoticeLink(String noticeLink) {
		this.noticeLink = noticeLink;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getNoticeEnableStatus() {
		return noticeEnableStatus;
	}
	public void setNoticeEnableStatus(Integer noticeEnableStatus) {
		this.noticeEnableStatus = noticeEnableStatus;
	}
	public String getNoticeImg() {
		return noticeImg;
	}
	public void setNoticeImg(String noticeImg) {
		this.noticeImg = noticeImg;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
}
