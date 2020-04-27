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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ze.zhou.dto.AreaExecution;
import com.ze.zhou.dto.CoordinateExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.enums.AreaStateEnum;
import com.ze.zhou.enums.CoordinateStateEnum;
import com.ze.zhou.service.AreaService;
import com.ze.zhou.service.CoordinateService;
import com.ze.zhou.service.OperatorService;
import com.ze.zhou.util.CodeUtil;
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
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private CoordinateService coordinateService;
	
	
	
	//更新站点信息状态
	@RequestMapping(value="/modifycoordinatestate",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyCoordinateState(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int coordinateId=HttpServletRequestUtil.getInt(request, "coordinateId");
		int coordinateEnableStatus=HttpServletRequestUtil.getInt(request, "enableStatus");
		int areaId=HttpServletRequestUtil.getInt(request, "areaId");
		if(coordinateId>0) {//获取到有效的id
			Coordinate coordinate=new Coordinate();
			coordinate.setCoordinateId(coordinateId);
			coordinate.setCoordinateEnableStatus(coordinateEnableStatus);
			if(areaId>0) {
				Area area=new Area();
				area.setAreaId(areaId);
				coordinate.setArea(area);
			}
			CoordinateExecution ce=coordinateService.changeCoordinate(coordinate);
			if(ce.getState()==CoordinateStateEnum.SUCCESS.getState()) {//更改成功
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "change failure");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty coordinateId");
		}
		return modelMap;
	}
	
	//获取站点信息
	@RequestMapping(value="/getcoordinatelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getCoordinateList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		CoordinateExecution ce=coordinateService.getCoordinateList(pageIndex, pageSize);
		if(ce.getState()==1) {//获取数据成功
			modelMap.put("coordinateList", ce.getCoordinateList());
			modelMap.put("count", ce.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty coordinate");
		}
		return modelMap;
	}
	
	//获取操作员信息
	@RequestMapping(value="/getoperatorlist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getOperatorList(){
		Map<String,Object> modelMap=new HashMap<>();
		List<Operator> operatorList=operatorService.getQueryOperator();
		if(operatorList!=null&&operatorList.size()>0) {
			modelMap.put("success", true);
			modelMap.put("operatorList", operatorList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty operator");
		}
		return modelMap;
	}
	
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
	@RequestMapping(value="/modifyareastate",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyAreaState(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int areaId=HttpServletRequestUtil.getInt(request, "areaId");
		int enableStatus=HttpServletRequestUtil.getInt(request, "enableStatus");
		int operatorId=HttpServletRequestUtil.getInt(request, "operatorId");
		Area area=new Area();
		area.setAreaId(areaId);
		area.setAreaEnableStatus(enableStatus);
		if(operatorId>0) {//判断是否分配管理员
			Operator operator=new Operator();
			operator.setOperatorId(operatorId);
			area.setOperator(operator);
		}
		int effectNum=areaService.changeArea(area);
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
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//获取从表单传递过来的信息
		String areaStr=HttpServletRequestUtil.getString(request, "areaStr");
		ObjectMapper mapper=new ObjectMapper();
		Area area=null;
		try {
			area=mapper.readValue(areaStr, Area.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if(area!=null) {
			//开始注册区域信息
			AreaExecution ae=areaService.addArea(area);
			if(ae.getState()==AreaStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("msg", ae.getStateInfo());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ae.getStateInfo());
			}
		}
		return modelMap;
	}
	//添加站点信息
	@RequestMapping(value="/registercoordinate",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerCoordinate(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//获取站点表单数据
		String coordinateStr=HttpServletRequestUtil.getString(request, "coordinateStr");
		ObjectMapper mapper=new ObjectMapper();
		Coordinate coordinate=null;
		try {
			coordinate=mapper.readValue(coordinateStr, Coordinate.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		if(coordinate!=null) {
			CoordinateExecution ce=coordinateService.addCoordinate(coordinate);
			if(ce.getState()==CoordinateStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ce.getStateInfo());
			}
		}
		return modelMap;
	}
}
