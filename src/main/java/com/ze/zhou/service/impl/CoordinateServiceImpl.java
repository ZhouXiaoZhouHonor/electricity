package com.ze.zhou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.CoordinateDao;
import com.ze.zhou.dao.PileDao;
import com.ze.zhou.dto.CoordinateExecution;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.enums.CoordinateStateEnum;
import com.ze.zhou.service.CoordinateService;


/*
	author:zhouze
	@createTime:2020年4月5日
	@goal:
*/
@Service
public class CoordinateServiceImpl implements CoordinateService{

	@Autowired
	private CoordinateDao coordinateDao;
	
	@Autowired
	private PileDao pileDao;
	/*
	 * @Param areaId
	 * @Return List<Coordinate>*/
	@Override
	public List<Coordinate> getCoordinateList(int areaId) {
		// TODO Auto-generated method stub
		return coordinateDao.queryCoordinate(areaId);
	}
	//添加站点信息
	@Override
	public CoordinateExecution addCoordinate(Coordinate coordinate) {
		CoordinateExecution ce=new CoordinateExecution();
		int effectNum;//存储影响行数
		if(coordinate!=null) {//判断站点信息是否为空
			effectNum=coordinateDao.insertCoordinate(coordinate);
			if(effectNum>0) {//插入成功
				ce.setState(CoordinateStateEnum.SUCCESS.getState());
				ce.setStateInfo("插入数据成功");
			}else {
				ce.setState(CoordinateStateEnum.INNER_ERROR.getState());
				ce.setStateInfo("插入数据失败");
			}
		}else {
			ce.setState(CoordinateStateEnum.NULL_Coordinate.getState());
			ce.setStateInfo("传递过来的数据为空，无法进行更新操作");
		}
		return ce;
	}
	//更新站点信息
	@Override
	public CoordinateExecution changeCoordinate(Coordinate coordinate) {
		//如果该站点不可用，那么该站点下的所有充电桩均不可用
		/*
		* 1、更新站点状态
		* 2、站点更新成功后，若状态被更改为不可用，则更新充电桩状态*/
		CoordinateExecution ce=new CoordinateExecution();
		int effectNum;
		if(coordinate!=null&&coordinate.getCoordinateId()>0) {
			effectNum=coordinateDao.updateCoordinate(coordinate);
			//若更改成功，则查看该站点是否可用，若不可用则更改充电桩状态
			if(effectNum>0&&coordinate.getCoordinateEnableStatus()!=1) {
				Pile pile=new Pile();
				pile.setCoordinate(coordinate);
				pile.setPileEnableStatus(0);
				effectNum=pileDao.updatePile(pile);
				if(effectNum>0) {//更新充电桩成功
					ce.setStateInfo("所属的充电桩更新成功");
				}
			}
			ce.setState(CoordinateStateEnum.SUCCESS.getState());
			ce.setStateInfo("更新站点成功");
		}else {
			ce.setState(CoordinateStateEnum.NULL_Coordinate.getState());
			ce.setStateInfo("empty coordinate or coordinateId");
		}
		return ce;
	}
}
