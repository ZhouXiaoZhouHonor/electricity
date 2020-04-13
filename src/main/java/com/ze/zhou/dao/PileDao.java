package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.Pile;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
public interface PileDao {
	//通过id查找充电桩
	/*
	 * @Param pileId
	 * @return list*/
	Pile queryPileById(Long pileId);
	
	//增加充电桩
	int insertPile(Pile pile);
	
	//更新充电桩信息
	int updatePile(Pile pile);
	
	//获取pileList，需要进行分页获取
	/*
	 * @Param pileCondition
	 * @Param rowIndex
	 * @Param pageSize
	 * @return pileList*/
	List<Pile> queryPileList(@Param("pileCondition") Pile pileCondition,
			@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	//获取查询的pile总数
	int queryPileCount(@Param("pileCondition") Pile pileCondition);
	
}
