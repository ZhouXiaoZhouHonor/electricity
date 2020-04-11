package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.Pile;
import com.ze.zhou.enums.PileStateEnum;

/*
	author:zhouze
	@createTime:2020年4月4日
	@goal:
*/
public class PileExecution {
	//结果状态
		private int state;
		//状态标识
		private String stateInfo;
		//店铺数量
		private int count;
		//操作的Shop(增删改店铺的时候用到)
		private Pile pile;
		//shop列表(查找店铺的时候用到)
		private List<Pile> pileList;
		
		//无参构造方法
		public PileExecution() {
			
		}
		
		//带有枚举类型参数的构造方法
		//主要用于店铺操作失败的情况下
		public PileExecution(PileStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
		}
		
		//成功的构造器
		//返回插入店铺的数据
		public PileExecution(PileStateEnum stateEnum,Pile pile) {
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
			this.pile=pile;
		}
		
		//成功的构造器
		//返回一个店铺对象列表
		public PileExecution(PileStateEnum stateEnum,List<Pile> pileList) {
			this.state=stateEnum.getState();
			this.stateInfo=stateEnum.getStateInfo();
			this.pileList=pileList;
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

		public Pile getPile() {
			return pile;
		}

		public void setPile(Pile pile) {
			this.pile = pile;
		}

		public List<Pile> getPileList() {
			return pileList;
		}

		public void setPileList(List<Pile> pileList) {
			this.pileList = pileList;
		}
		
		
}
