package com.ze.zhou.enums;
/*
	author:zhouze
	@createTime:2020年5月26日
	@goal:
*/
public enum PileElectricityStateEnum {
	CHECK(0,"审核中"),
	OFFLINE(-1,"非法区域"),
	SUCCESS(1,"操作成功"),
	FAILURE(0,"操作失败"),
	PASS(2,"通过认证"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_PILEELECTRICITYID(-1002,"pileElectricityId为空"),
	NULL_PILEELECTRICITY(-1003,"pileElectricity信息为空");
	private int state;
	private String stateInfo;
	
	private PileElectricityStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	//依据传入的state返回响应的中文信息stateInfo
	public static PileElectricityStateEnum stateOf(int state) {
		for(PileElectricityStateEnum stateEnum:values()) {
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
