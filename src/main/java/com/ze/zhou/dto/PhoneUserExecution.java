package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.PhoneUser;
import com.ze.zhou.enums.PhoneUserStateEnum;

/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
public class PhoneUserExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Shop(增删改店铺的时候用到)
	private PhoneUser phoneUser;
	//shop列表(查找店铺的时候用到)
	private List<PhoneUser> phoneUserList;
	
	//无参构造方法
	public PhoneUserExecution() {
		
	}
	
	//带有枚举类型参数的构造方法
	//主要用于店铺操作失败的情况下
	public PhoneUserExecution(PhoneUserStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	//成功的构造器
	//返回插入店铺的数据
	public PhoneUserExecution(PhoneUserStateEnum stateEnum,PhoneUser phoneUser) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.phoneUser=phoneUser;
	}
	
	//成功的构造器
	//返回一个店铺对象列表
	public PhoneUserExecution(PhoneUserStateEnum stateEnum,List<PhoneUser> phoneUserList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.phoneUserList=phoneUserList;
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

	public PhoneUser getPhoneUser() {
		return phoneUser;
	}

	public void setPhoneUser(PhoneUser phoneUser) {
		this.phoneUser = phoneUser;
	}

	public List<PhoneUser> getPhoneUserList() {
		return phoneUserList;
	}

	public void setPhoneUserList(List<PhoneUser> phoneUserList) {
		this.phoneUserList = phoneUserList;
	}
}
