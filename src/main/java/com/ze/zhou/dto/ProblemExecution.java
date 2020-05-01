package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.Problem;
import com.ze.zhou.enums.ProblemStateEnum;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public class ProblemExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量
	private int count;
	//操作的Shop(增删改店铺的时候用到)
	private Problem problem;
	//shop列表(查找店铺的时候用到)
	private List<Problem> problemList;
	
	//无参构造方法
	public ProblemExecution() {
		
	}
	
	//带有枚举类型参数的构造方法
	//主要用于店铺操作失败的情况下
	public ProblemExecution(ProblemStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	
	//成功的构造器
	//返回插入店铺的数据
	public ProblemExecution(ProblemStateEnum stateEnum,Problem problem) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.problem=problem;
	}
	
	//成功的构造器
	//返回一个店铺对象列表
	public ProblemExecution(ProblemStateEnum stateEnum,List<Problem> problemList) {
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.problemList=problemList;
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

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public List<Problem> getProblemList() {
		return problemList;
	}

	public void setProblemList(List<Problem> problemList) {
		this.problemList = problemList;
	}
}
