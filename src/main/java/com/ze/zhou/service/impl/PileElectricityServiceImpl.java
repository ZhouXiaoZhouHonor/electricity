package com.ze.zhou.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.PileElectricityDao;
import com.ze.zhou.dto.PileElectricityExecution;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.entity.PileElectricity;
import com.ze.zhou.enums.PileElectricityStateEnum;
import com.ze.zhou.service.PileElectricityService;
import com.ze.zhou.web.operatoradmin.WatchPileElectricity;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年4月16日
	@goal:
*/
@Service
public class PileElectricityServiceImpl implements PileElectricityService{

	Logger logger=(Logger) LoggerFactory.getLogger(PileElectricityServiceImpl.class);
	@Autowired
	private PileElectricityDao pileElectricityDao;
	
	/*按照时间和对应的pileId获取充电桩的历史记录用于展示。每一个充电桩的历史数据是很多的。
	 * 存储的话，应该是List<List<PileElectricity>>*/
	@Override
	public List<PileElectricity> getPileElectricityByPileIdAndDate(int pileId, 
			Date firstTime, Date endTime) {
		// TODO Auto-generated method stub
		return pileElectricityDao.queryPileElectricityListByPileIdAndDate(pileId, 
				firstTime, endTime);
	}

	@Override
	public PileElectricityExecution addPileElectricity(List<PileElectricity> pileElectricityList) {
		PileElectricityExecution pee=new PileElectricityExecution();
		//每次添加都是20条数据同时添加
		if(pileElectricityList!=null&&pileElectricityList.size()==20) {
			//将内部数据进行补全
			for(PileElectricity pe:pileElectricityList) {
				pe.setElectricityReactiveEnergy(0.00f);
				pe.setElectricityActiveEnergy(0.00f);
				pe.setElectricityActivePower(0.00f);
				pe.setPileElectricityActiveEnergy(0.00f);
				pe.setPileElectricityActivePower(0.00f);
				pe.setPileElectricityReactiveEnergy(0.00f);
				Pile pile=new Pile();
				pile.setPileId(23L);
				pe.setPile(pile);
			}
			int result=pileElectricityDao.insertPileElectricity(pileElectricityList);
			if(result>0) {
				pee.setState(PileElectricityStateEnum.SUCCESS.getState());
				logger.debug("插入电量数据成功");
			}else {
				pee.setState(PileElectricityStateEnum.FAILURE.getState());
			}
		}else {
			pee.setState(PileElectricityStateEnum.NULL_PILEELECTRICITY.getState());
		}
		return pee;
	}
}
