package com.ze.zhou.web.operatoradmin;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ze.zhou.entity.PileElectricity;
import com.ze.zhou.service.PileElectricityService;
import com.ze.zhou.util.HttpServletRequestUtil;
import com.ze.zhou.util.PileMachine;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

/*
	author:zhouze
	@createTime:2020年4月16日
	@goal:
*/
@Controller
@RequestMapping("/watchpileelectricity")
public class WatchPileElectricity {
	@Autowired
	private PileElectricityService pileElectricityService;
	
	@RequestMapping(value="/getpileelectricity",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getPileElectriciy(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//获取pileId
		int pileId=HttpServletRequestUtil.getInt(request, "pileId");
		//获取时间范围
		Date firstTime=HttpServletRequestUtil.getDate(request, "firstTime");//起始时间
		Date endTime=HttpServletRequestUtil.getDate(request, "endTime");//终止时间
		if(pileId>0&&firstTime!=null&&endTime!=null) {
			List<PileElectricity> pileElectricityList=pileElectricityService.getPileElectricityByPileIdAndDate(pileId, firstTime, endTime);
			if(pileElectricityList!=null&&pileElectricityList.size()>0) {
				modelMap.put("success", true);
				modelMap.put("pileElectricityList", pileElectricityList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "empty pileElectricityList");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pileId or firstTime or endTime");
		}
		return modelMap;
	}
	
	//根据数据信息动态将数据添加至数据库中
	@RequestMapping(value="/watchpile",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> watchPile(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//通过上位机拿到数据(数据为10条)
		try {
			List<Map<String,Float>> resultList=PileMachine.getElectricityResult();
			if(resultList!=null&&resultList.size()>=1) {
				modelMap.put("success", true);
				modelMap.put("resultList", resultList);
			}else {
				modelMap.put("success", false);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		return modelMap;
	}
}
