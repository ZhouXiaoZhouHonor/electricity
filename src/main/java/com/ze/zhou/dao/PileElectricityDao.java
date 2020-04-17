package com.ze.zhou.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.PileElectricity;

/*
	author:zhouze
	@createTime:2020年4月15日
	@goal:
*/
public interface PileElectricityDao {
	//通过pileId和时间来获取对应的电力数据集合对象
	List<PileElectricity> queryPileElectricityListByPileIdAndDate(
			@Param("pileId")int pileId,
			@Param("firstTime")Date firstTime,@Param("endTime")Date endTime);
}
