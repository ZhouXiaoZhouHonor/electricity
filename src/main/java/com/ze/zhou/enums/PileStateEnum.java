package com.ze.zhou.enums;


/*
	author:zhouze
	@createTime:2020年4月3日
	@goal:
*/
public enum PileStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法充电桩"),
	SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_PILEID(-1002,"pileId为空"),
	NULL_PILE(-1003,"pile信息为空");
	private int state;
	private String stateInfo;
	
	private PileStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//依据传入的state返回响应的中文信息stateInfo
	public static PileStateEnum stateOf(int state) {
		for(PileStateEnum stateEnum:values()) {
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
