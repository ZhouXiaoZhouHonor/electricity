package com.ze.zhou.service;

import java.util.Date;
import java.util.List;

import com.ze.zhou.dto.PileElectricityExecution;
import com.ze.zhou.entity.PileElectricity;

/*
	author:zhouze
	@createTime:2020年4月16日
	@goal:
*/
public interface PileElectricityService {
	//获取List<PileElectricity>
	List<PileElectricity> getPileElectricityByPileIdAndDate(int pileId,
			Date firstTime,Date endTime);
	
	//添加pileElectricity
	PileElectricityExecution addPileElectricity(List<PileElectricity> pileElectricityList,int pileId);
}
