package com.ze.zhou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.AreaDao;
import com.ze.zhou.dto.AreaExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.enums.AreaStateEnum;
import com.ze.zhou.service.AreaService;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
@Service
public class AreaServiceImpl implements AreaService{

	@Autowired
	private AreaDao areaDao;
	//返回所有区域信息
	@Override
	public List<Area> getQueryArea() {
		// TODO Auto-generated method stub
		return areaDao.queryArea();
	}
	
	@Override
	public List<Area> getQueryAreaByOperator(Area areaCondition) {
		// TODO Auto-generated method stub
		
		return areaDao.queryAreaByOperator(areaCondition);
	}

	//添加区域信息
	//TODO 需要设置事务回滚操作
	@Override
	public AreaExecution addArea(Area area) {
		AreaExecution ae=new AreaExecution();
		int effectNum=areaDao.insertArea(area);
		if(effectNum>0) {
			ae.setState(AreaStateEnum.SUCCESS.getState());
			ae.setStateInfo("添加区域信息成功");
		}else {
			ae.setState(AreaStateEnum.OFFLINE.getState());
			ae.setStateInfo("添加区域信息失败");
		}
		return null;
	}

}
