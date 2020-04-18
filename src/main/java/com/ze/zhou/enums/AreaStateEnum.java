package com.ze.zhou.enums;
/*
	author:zhouze
	@createTime:2020年4月17日
	@goal:
*/
public enum AreaStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法区域"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_AREAID(-1002,"areaId为空"),
	NULL_AREA(-1003,"area信息为空");
	private int state;
	private String stateInfo;
	
	private AreaStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//依据传入的state返回响应的中文信息stateInfo
	public static AreaStateEnum stateOf(int state) {
		for(AreaStateEnum stateEnum:values()) {
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
