package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.dto.AreaExecution;
import com.ze.zhou.entity.Area;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/

public interface AreaService {
	//查找全部的area
	List<Area> getQueryArea();
	//根据区域信息查找对应的area
	List<Area> getQueryAreaByOperator(Area areaCondition);
	//添加area
	AreaExecution addArea(Area area);
}
