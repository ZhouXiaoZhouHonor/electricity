package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.entity.Area;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/

public interface AreaService {
	List<Area> getQueryArea();
	
	List<Area> getQueryAreaByOperator(Area areaCondition);
}
