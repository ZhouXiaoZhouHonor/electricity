package com.ze.zhou.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.entity.Pile;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
public class PileDaoTest extends BaseTest{
	@Autowired
	private PileDao pileDao;
	
	@Test
	@Ignore
	public void queryPileListTest() {
		Pile pileCondition=new Pile();
		Coordinate coordinate=new Coordinate();
		coordinate.setCoordinateId(1);
		Operator operator=new Operator();
		operator.setOperatorId(1);
		pileCondition.setCoordinate(coordinate);
		pileCondition.setOperator(operator);
		//pileCondition.setPileName("合肥学院1");
		List<Pile> pileList=pileDao.queryPileList(pileCondition,0,3);
		int number=pileDao.queryPileCount(pileCondition);
		System.out.println("number:"+number);
		System.out.println(pileList.size());
	}
	
	@Test
	//@Ignore
	public void updatePileTest() {
		/*
		Pile pile=new Pile();
		pile.setPileId(4L);
		pile.setPileName("充电桩3-1");
		pile.setPileLongitude(117.2549271);
		pile.setPileLatitude(31.7519211+0.000004);
		pile.setPileImg("img3-1");
		pile.setPileEnableStatus(1);
		pile.setPileDesc("汽车充电桩1");
		pile.setPileAddr("合肥学院停车场1");
		Area area=new Area();
		area.setAreaId(2);
		Operator operator=new Operator();
		operator.setOperatorId(1);
		Coordinate coordinate=new Coordinate();
		coordinate.setCoordinateId(1);
		pile.setArea(area);
		pile.setCoordinate(coordinate);
		pile.setOperator(operator);
		int effectNum=pileDao.updatePile(pile);
		assertEquals(1,effectNum);
		*/
		//根据coordinateId来更新，主要是批量更新充电桩状态
		/*
		Coordinate c=new Coordinate();
		c.setCoordinateId(1);
		Pile p=new Pile();
		p.setCoordinate(c);
		p.setPileEnableStatus(1);
		int effectNum=pileDao.updatePile(p);
		System.out.println(effectNum);
		*/
	}
	
	@Test
	@Ignore
	public void addPileTest() {
		Pile pile=new Pile();
		pile.setPileName("充电桩3");
		pile.setPileLongitude(117.2549270);
		pile.setPileLatitude(31.7519210+0.000004);
		pile.setPileImg("img3");
		pile.setPileEnableStatus(1);
		pile.setPileDesc("汽车充电桩");
		pile.setPileAddr("合肥学院停车场");
		Area area=new Area();
		area.setAreaId(2);
		Operator operator=new Operator();
		operator.setOperatorId(1);
		Coordinate coordinate=new Coordinate();
		coordinate.setCoordinateId(1);
		pile.setArea(area);
		pile.setCoordinate(coordinate);
		pile.setOperator(operator);
		pile.setPileNumber(5);
		int effNum=pileDao.insertPile(pile);
		assertEquals(1,effNum);
	}
	
	@Test
	@Ignore
	public void queryPileById() {
		Pile pile=pileDao.queryPileById(2L);
		System.out.println(pile.getPileId());
	}
	
}
