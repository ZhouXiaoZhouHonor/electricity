package com.ze.zhou.enums;
/*
	author:zhouze
	@createTime:2020年5月8日
	@goal:
*/
public enum OperatorStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法区域"),
	SUCCESS(1,"操作成功"),
	CHECK_SUCCESS(11,"验证成功"),
	CHECK_FAILURE(10,"验证失败"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_OPERATORID(-1002,"operatorId为空"),
	NULL_OPERATOR(-1003,"operator信息为空");
	private int state;
	private String stateInfo;
	
	private OperatorStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//依据传入的state返回响应的中文信息stateInfo
	public static OperatorStateEnum stateOf(int state) {
		for(OperatorStateEnum stateEnum:values()) {
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
