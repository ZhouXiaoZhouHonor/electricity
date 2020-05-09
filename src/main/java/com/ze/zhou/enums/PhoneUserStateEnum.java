package com.ze.zhou.enums;
/*
	author:zhouze
	@createTime:2020年5月9日
	@goal:
*/
public enum PhoneUserStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法区域"),
	SUCCESS(1,"操作成功"),
	FAILURE(10,"操作失败"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_PHONEUSERID(-1002,"phoneUserId为空"),
	NULL_PHONEUSER(-1003,"phoneUser信息为空");
	private int state;
	private String stateInfo;
	
	private PhoneUserStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//依据传入的state返回响应的中文信息stateInfo
	public static PhoneUserStateEnum stateOf(int state) {
		for(PhoneUserStateEnum stateEnum:values()) {
			if(stateEnum.getState()==state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	} 
}
