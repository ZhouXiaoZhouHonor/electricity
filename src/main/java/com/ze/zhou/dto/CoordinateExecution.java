package com.ze.zhou.dto;

import java.util.List;

import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.enums.CoordinateStateEnum;

/*
	author:zhouze
	@createTime:2020年4月24日
	@goal:
*/
public class CoordinateExecution {
	//结果状态
			private int state;
			//状态标识
			private String stateInfo;
			//店铺数量
			private int count;
			//操作的Shop(增删改店铺的时候用到)
			private Coordinate coordinate;
			//shop列表(查找店铺的时候用到)
			private List<Coordinate> coordinateList;
			
			//无参构造方法
			public CoordinateExecution() {
				
			}
			
			//带有枚举类型参数的构造方法
			//主要用于店铺操作失败的情况下
			public CoordinateExecution(CoordinateStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
				this.state=stateEnum.getState();
				this.stateInfo=stateEnum.getStateInfo();
			}
			
			//成功的构造器
			//返回插入店铺的数据
			public CoordinateExecution(CoordinateStateEnum stateEnum,Coordinate coordinate) {
				this.state=stateEnum.getState();
				this.stateInfo=stateEnum.getStateInfo();
				this.coordinate=coordinate;
			}
			
			//成功的构造器
			//返回一个店铺对象列表
			public CoordinateExecution(CoordinateStateEnum stateEnum,List<Coordinate> coordinateList) {
				this.state=stateEnum.getState();
				this.stateInfo=stateEnum.getStateInfo();
				this.coordinateList=coordinateList;
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

			public Coordinate getCoordinate() {
				return coordinate;
			}

			public void setCoordinate(Coordinate coordinate) {
				this.coordinate = coordinate;
			}

			public List<Coordinate> getCoordinateList() {
				return coordinateList;
			}

			public void setCoordinateList(List<Coordinate> coordinateList) {
				this.coordinateList = coordinateList;
			}
			
}
