package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.Notice;
import com.ze.zhou.enums.NoticeStateEnum;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public class NoticeExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Shop(增删改店铺的时候用到)
	private Notice notice;
	//shop列表(查找店铺的时候用到)
	private List<Notice> noticeList;
	
	//无参构造方法
	public NoticeExecution() {
		
	}
	
	//带有枚举类型参数的构造方法
	//主要用于店铺操作失败的情况下
	public NoticeExecution(NoticeStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	//成功的构造器
	//返回插入店铺的数据
	public NoticeExecution(NoticeStateEnum stateEnum,Notice notice) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.notice=notice;
	}
	
	//成功的构造器
	//返回一个店铺对象列表
	public NoticeExecution(NoticeStateEnum stateEnum,List<Notice> noticeList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.noticeList=noticeList;
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

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public List<Notice> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<Notice> noticeList) {
		this.noticeList = noticeList;
	}
}
