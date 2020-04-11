package com.ze.zhou.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.util.ImageHolder;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
public class PileServiceTest extends BaseTest{

	@Autowired
	private PileService pileService;
	
	@Test
	public void addPileTest() {
		Pile pile=new Pile();
		pile.setPileName("充电桩4");
		pile.setPileLongitude(117.2549270);
		pile.setPileLatitude(31.7519210+0.000006);
		//pile.setPileImg("img3");
		InputStream is = null;
		try {
			is=new FileInputStream("E:/Images/yellow.jpg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageHolder ih=new ImageHolder();
		ih.setImage(is);
		ih.setImageName("yellow.jpg");
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
		pileService.addPile(pile, ih);
	}
	
	@Test
	@Ignore
	public void getPileByIdTest() {
		pileService.getPileById(1L);
	}
}
