package com.ze.zhou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.AreaDao;
import com.ze.zhou.dto.AreaExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.enums.AreaStateEnum;
import com.ze.zhou.service.AreaService;
import com.ze.zhou.util.PageCalculator;

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
		//后台添加创建时间和默认状态
		area.setCreateTime(new Date());
		area.setLastEditTime(new Date());
		area.setAreaEnableStatus(1);
		int effectNum=areaDao.insertArea(area);
		if(effectNum>0) {
			ae.setState(AreaStateEnum.SUCCESS.getState());
			ae.setStateInfo("添加区域信息成功");
		}else {
			ae.setState(AreaStateEnum.OFFLINE.getState());
			ae.setStateInfo("添加区域信息失败");
		}
		return ae;
	}

	//根据分页获取全部数据并进行显示
	@Override
	public AreaExecution getQueryAreaPage(int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Area> areaList=areaDao.queryAreaPage(rowIndex, pageSize);
		//获取同等条件下查询出来的总数
		int count=areaDao.queryAreaCount();
		AreaExecution ae=new AreaExecution();
		if(areaList!=null&&areaList.size()>0) {
			ae.setAreaList(areaList);
			ae.setCount(count);
			ae.setState(AreaStateEnum.SUCCESS.getState());
		}else {
			ae.setState(AreaStateEnum.NULL_AREA.getState());
			ae.setStateInfo("empty area");
		}
		return ae;
	}

	//更新area的状态
	@Override
	public int changeArea(Area area) {
		return areaDao.updateArea(area);
	}
}
