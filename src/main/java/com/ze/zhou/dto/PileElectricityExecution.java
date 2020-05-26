package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.PileElectricity;
import com.ze.zhou.enums.PileElectricityStateEnum;

/*
	author:zhouze
	@createTime:2020年5月26日
	@goal:
*/
public class PileElectricityExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Shop(增删改店铺的时候用到)
	private PileElectricity pileElectricity;
	//shop列表(查找店铺的时候用到)
	private List<PileElectricity> pileElectricityList;
	
	//无参构造方法
	public PileElectricityExecution() {
		
	}
	
	//带有枚举类型参数的构造方法
	//主要用于店铺操作失败的情况下
	public PileElectricityExecution(PileElectricityStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	//成功的构造器
	//返回插入店铺的数据
	public PileElectricityExecution(PileElectricityStateEnum stateEnum,PileElectricity pileElectricity) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.pileElectricity=pileElectricity;
	}
	
	//成功的构造器
	//返回一个店铺对象列表
	public PileElectricityExecution(PileElectricityStateEnum stateEnum,List<PileElectricity> pileElectricityList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.pileElectricityList=pileElectricityList;
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

	public PileElectricity getPileElectricity() {
		return pileElectricity;
	}

	public void setPileElectricity(PileElectricity pileElectricity) {
		this.pileElectricity = pileElectricity;
	}

	public List<PileElectricity> getPileElectricityList() {
		return pileElectricityList;
	}

	public void setPileElectricityList(List<PileElectricity> pileElectricityList) {
		this.pileElectricityList = pileElectricityList;
	}
	
}
