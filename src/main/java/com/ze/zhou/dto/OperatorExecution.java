package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.enums.AreaStateEnum;
import com.ze.zhou.enums.OperatorStateEnum;

/*
	author:zhouze
	@createTime:2020年5月8日
	@goal:
*/
public class OperatorExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Operator(增删改店铺的时候用到)
	private Operator operator;
	//shop列表(查找店铺的时候用到)
	private List<Operator> operatorList;
	
	//无参构造方法
	public OperatorExecution() {
		
	}
	
	//带有枚举类型参数的构造方法
	//主要用于店铺操作失败的情况下
	public OperatorExecution(OperatorStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	//成功的构造器
	//返回插入店铺的数据
	public OperatorExecution(OperatorStateEnum stateEnum,Operator operator) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.operator=operator;
	}
	
	//成功的构造器
	//返回一个店铺对象列表
	public OperatorExecution(OperatorStateEnum stateEnum,List<Operator> operatorList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.operatorList=operatorList;
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

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public List<Operator> getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(List<Operator> operatorList) {
		this.operatorList = operatorList;
	}
	
	
}
