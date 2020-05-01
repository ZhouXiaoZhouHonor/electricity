package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.ProblemImg;
import com.ze.zhou.enums.ProblemImgStateEnum;

/*
	author:zhouze
	@createTime:2020年4月29日
	@goal:
*/
public class ProblemImgExecution {
	//结果状态
		private int state;
		//状态标识
		private String stateInfo;
		//店铺数量
		private int count;
		//操作的Shop(增删改店铺的时候用到)
		private ProblemImg problemImg;
		//shop列表(查找店铺的时候用到)
		private List<ProblemImg> problemImgList;
		
		//无参构造方法
		public ProblemImgExecution() {
			
		}
		
		//带有枚举类型参数的构造方法
		//主要用于店铺操作失败的情况下
		public ProblemImgExecution(ProblemImgStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
		}
		
		//成功的构造器
		//返回插入店铺的数据
		public ProblemImgExecution(ProblemImgStateEnum stateEnum,ProblemImg problemImg) {
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
			this.problemImg=problemImg;
		}
		
		//成功的构造器
		//返回一个店铺对象列表
		public ProblemImgExecution(ProblemImgStateEnum stateEnum,List<ProblemImg> problemImgList) {
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
			this.problemImgList=problemImgList;
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

		public ProblemImg getProblemImg() {
			return problemImg;
		}

		public void setProblemImg(ProblemImg problemImg) {
			this.problemImg = problemImg;
		}

		public List<ProblemImg> getProblemImgList() {
			return problemImgList;
		}

		public void setProblemImgList(List<ProblemImg> problemImgList) {
			this.problemImgList = problemImgList;
		}
}
