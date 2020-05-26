package com.ze.zhou.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.entity.PileElectricity;

/*
	author:zhouze
	@createTime:2020年4月15日
	@goal:
*/
public class PileElectricityDaoTest extends BaseTest{
	@Autowired
	private PileElectricityDao pileElectricityDao;
	
	
	@Test
	@Ignore
	public void insertPileElectricity() {
		List<PileElectricity> list=new ArrayList<>();
		for(int i=0;i<3;i++) {
			PileElectricity pi=new PileElectricity();
			Pile pile=new Pile();
			pile.setPileId(23L);
			pi.setElectricityActiveEnergy(2.0f);
			pi.setElectricityActivePower(2.0f);
			pi.setElectricityHz(2.0f);
			pi.setElectricityI(2.0f);
			pi.setElectricityReactiveEnergy(2.0f);
			pi.setElectricityV(2.0f);
			pi.setPile(pile);
			pi.setPileElectricityActiveEnergy(2.0f);
			pi.setPileElectricityActivePower(2.0f);
			pi.setPileElectricityHz(2.0f);
			pi.setPileElectricityI(2.0f);
			pi.setPileElectricityReactiveEnergy(2.0f);
			pi.setPileElectricityTime(new Date());
			pi.setPileElectricityV(2.0f);
			list.add(pi);
		}
		pileElectricityDao.insertPileElectricity(list);
		//System.out.println(result);
	}
	
	@Test
	@Ignore
	public void queryPileElectricityListByPileIdAndDateTest() {
		Date firstTime = null;
		Date endTime = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			firstTime=sdf.parse("2020-04-15 22:06:18");
			endTime=sdf.parse("2020-04-15 22:06:22"); 
		}catch(Exception e) {
			e.printStackTrace(); 
		}
		System.out.println(firstTime==null);
		System.out.println(endTime==null);
		System.out.println(firstTime.getTime());
		System.out.println(endTime.getTime());
		List<PileElectricity> pe=pileElectricityDao.queryPileElectricityListByPileIdAndDate(23, firstTime, endTime);
		System.out.println(pe.size());
	}
}








