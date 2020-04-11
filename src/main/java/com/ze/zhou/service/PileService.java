package com.ze.zhou.service;


import java.util.List;

import com.ze.zhou.dto.PileExecution;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.util.ImageHolder;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
public interface PileService {
	//通过pile_id获取充电桩的信息
	Pile getPileById(Long pileId);
	
	//添加充电桩
	PileExecution addPile(Pile pile,ImageHolder thumbnail);
	
	//更新充电桩
	PileExecution modifyPile(Pile pile,ImageHolder thumbnail);
	
	//获取充电桩集合
	PileExecution getPileList(Pile pile,int pageIndex,int pageSize);
}
