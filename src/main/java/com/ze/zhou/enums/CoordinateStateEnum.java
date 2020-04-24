package com.ze.zhou.enums;
/*
	author:zhouze
	@createTime:2020年4月24日
	@goal:
*/
public enum CoordinateStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法区域"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_CoordinateID(-1002,"coordinateId为空"),
	NULL_Coordinate(-1003,"coordinate信息为空");
	private int state;
	private String stateInfo;
	
	private CoordinateStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//依据传入的state返回响应的中文信息stateInfo
	public static CoordinateStateEnum stateOf(int state) {
		for(CoordinateStateEnum stateEnum:values()) {
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
