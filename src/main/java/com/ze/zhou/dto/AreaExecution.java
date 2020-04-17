package com.ze.zhou.dto;

import java.util.List;
import com.ze.zhou.entity.Area;
import com.ze.zhou.enums.AreaStateEnum;

/*
	author:zhouze
	@createTime:2020年4月17日
	@goal:
*/
public class AreaExecution {
	//结果状态
			private int state;
			//状态标识
			private String stateInfo;
			//店铺数量
			private int count;
			//操作的Shop(增删改店铺的时候用到)
			private Area area;
			//shop列表(查找店铺的时候用到)
			private List<Area> areaList;
			
			//无参构造方法
			public AreaExecution() {
				
			}
			
			//带有枚举类型参数的构造方法
			//主要用于店铺操作失败的情况下
			public AreaExecution(AreaStateEnum stateEnum) {//需要在enums包下创建ShopEnum枚举类
				this.state=stateEnum.getState();
				this.stateInfo=stateEnum.getStateInfo();
			}
			
			//成功的构造器
			//返回插入店铺的数据
			public AreaExecution(AreaStateEnum stateEnum,Area area) {
				this.state=stateEnum.getState();
				this.stateInfo=stateEnum.getStateInfo();
				this.area=area;
			}
			
			//成功的构造器
			//返回一个店铺对象列表
			public AreaExecution(AreaStateEnum stateEnum,List<Area> areaList) {
				this.state=stateEnum.getState();
				this.stateInfo=stateEnum.getStateInfo();
				this.areaList=areaList;
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

			public Area getArea() {
				return area;
			}

			public void setArea(Area area) {
				this.area = area;
			}

			public List<Area> getAreaList() {
				return areaList;
			}

			public void setAreaList(List<Area> areaList) {
				this.areaList = areaList;
			}
}
