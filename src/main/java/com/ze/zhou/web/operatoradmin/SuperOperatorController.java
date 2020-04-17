package com.ze.zhou.web.operatoradmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ze.zhou.dto.AreaExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.service.AreaService;

/*
	author:zhouze
	@createTime:2020年4月17日
	@goal:
*/
@Controller
@RequestMapping("/superoperator")
public class SuperOperatorController {
	@Autowired
	private AreaService areaService;
	
	//获取区域信息
	@RequestMapping(value="/getarealist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getAreaList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//TODO 从session中获取operatorId
		//先写死
		Operator operator=new Operator();
		operator.setOperatorId(1);
		Area areaCondition=new Area();
		areaCondition.setOperator(operator);
		List<Area> areaList=areaService.getQueryAreaByOperator(areaCondition);
		if(areaList!=null) {
			modelMap.put("success", true);
			modelMap.put("areaList", areaList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty area");
		}
		return modelMap;
	}
	
	//添加区域信息
	@RequestMapping(value="/registerarea",method=RequestMethod.POST)
	private Map<String,Object> registerArea(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		
		return modelMap;
	}
	
}
