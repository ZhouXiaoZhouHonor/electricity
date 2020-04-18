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
import com.ze.zhou.util.HttpServletRequestUtil;

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
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		AreaExecution ae=areaService.getQueryAreaPage(pageIndex, pageSize);
		if(ae.getState()==1) {//获取数据成功
			modelMap.put("success", true);
			modelMap.put("areaList", ae.getAreaList());
			modelMap.put("count", ae.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty area");
		}
		return modelMap;
	}
	
	//更新区域信息状态
	@RequestMapping(value="/modifyareastate",method=RequestMethod.GET)
	private Map<String,Object> modifyAreaState(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int areaId=HttpServletRequestUtil.getInt(request, "areaId");
		int effectNum=areaService.changeAreaState(areaId);
		if(effectNum==1) {
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "更新失败");
		}
		return modelMap;
	}
	
	//添加区域信息
	@RequestMapping(value="/registerarea",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerArea(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		
		return modelMap;
	}
	
}
