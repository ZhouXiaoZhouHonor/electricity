package com.ze.zhou.web.operatoradmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ze.zhou.dto.CoordinateExecution;
import com.ze.zhou.dto.PhoneUserExecution;
import com.ze.zhou.dto.PileExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.enums.CoordinateStateEnum;
import com.ze.zhou.enums.PhoneUserStateEnum;
import com.ze.zhou.enums.PileStateEnum;
import com.ze.zhou.service.AreaService;
import com.ze.zhou.service.CoordinateService;
import com.ze.zhou.service.PhoneUserService;
import com.ze.zhou.service.PileService;
import com.ze.zhou.util.CodeUtil;
import com.ze.zhou.util.HttpServletRequestUtil;
import com.ze.zhou.util.ImageHolder;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
@Controller
@RequestMapping("/operator")
public class OperatorController {
	//在编写程序的过程中需要添加日志才可以，这样方便查找错误
	Logger logger=(Logger) LoggerFactory.getLogger(OperatorController.class);
	@Autowired
	private PileService pileService;
	
	@Autowired 
	private AreaService areaService;
	
	@Autowired
	private CoordinateService coordinateService;
	
	@Autowired
	private PhoneUserService phoneUserService;
	
	//获取用户数量
	@RequestMapping(value="/getusernumber",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getUserNumber(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int userOnline=HttpServletRequestUtil.getInt(request, "userOnline");
		PhoneUserExecution pue=phoneUserService.getCountUser(userOnline);
		if(pue.getState()==PhoneUserStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
			modelMap.put("count", pue.getCount());
		}else {
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	//获取coordinate
	@RequestMapping(value="/getcoordinate",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getCoordinate(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int coordinateId=HttpServletRequestUtil.getInt(request, "coordinateId");
		if(coordinateId>0) {
			CoordinateExecution ce=coordinateService.getCoordinate(coordinateId);
			if(ce.getState()==CoordinateStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
				modelMap.put("coordinate", ce.getCoordinate());
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "empty coordinate");
			}
		}else {
			modelMap.put("success", false);
		}
		return modelMap;
	}
	
	//获取pileList
	@RequestMapping(value="/getpilelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getPileList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//从路径中取出参数:coordinateId
		int coordinateId=HttpServletRequestUtil.getInt(request, "coordinateId");
		//从路径中获取页码和条数
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		
		/*1、首先从session获取pileList，若session中的pileList不为空，则获取
		 * 2、session中的pileList为空则从后台数据库中获取*/
		@SuppressWarnings("unchecked")
		List<Pile> pileList=(List<Pile>) request.getSession().getAttribute("pileList");
		//不用于分页显示且session中有pileList
		if(pileList!=null&&pileList.size()>0&&pageSize<=0&&pageIndex<0) {
			modelMap.put("success", true);
			modelMap.put("pileList",pileList);
			return modelMap;
		}
		
/*当第一次从数据库中获取pileList后，将其放入session中*/
		
		//从session中获取operator
		Operator operator=(Operator) request.getSession().getAttribute("operatorCurrent");
		//先使用设值注入的方法
		//Operator operator=new Operator();
		//operator.setOperatorId(1);
		//设置充电桩的传值对象
		Pile pileCondition=new Pile();
		pileCondition.setOperator(operator);
		
		//如果分页需要的参数没有传递过来，那么需要先获取该管理员下有多少充电桩的数量,防止分页无法获得，造成sql查询失败
		if(pageIndex<0&&pageSize<0) {
			pageSize=pileService.getQueryPileListCount(pileCondition);
		}
		//从路径中获取充电桩名称
		String pileName=HttpServletRequestUtil.getString(request, "pileName");
		//从路径中获取区域信息
		int areaId=HttpServletRequestUtil.getInt(request, "areaId");
		
		if(coordinateId>0||(operator!=null&&operator.getOperatorId()>0)||(pageIndex>=0&&pageSize>0)||pileName!=null||areaId>0) {
			//创建查询对象
			Coordinate coordinate=new Coordinate();
			coordinate.setCoordinateId(coordinateId);
			pileCondition.setCoordinate(coordinate);
			pileCondition.setPileName(pileName);
			Area area=new Area();
			area.setAreaId(areaId);
			pileCondition.setArea(area);
			PileExecution pe=pileService.getPileList(pileCondition,pageIndex,pageSize);
			//当session中没有pile时，将pile放入session存储。该逻辑中还包含分页查询，不能将分页查询的结果放入session中
			if(pileList==null||pileList.size()==0) {
				request.getSession().setAttribute("pileList", pe.getPileList());
			}
			logger.info("获取pileList成功");
			modelMap.put("success", true);
			modelMap.put("pileList", pe.getPileList());
			modelMap.put("count", pe.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty coordinateId or operatorId or pageIndex or pageSize");
		}
		return modelMap;
	}
	
	//获取单个pile
	@RequestMapping(value="/getpilebyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getPileById(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		Long pileId=HttpServletRequestUtil.getLong(request, "pileId");
		if(pileId>0&&pileId!=null) {//存在pileId
			Pile pile=pileService.getPileById(pileId);
			modelMap.put("success", true);
			modelMap.put("pile", pile);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pileId");
		}
		return modelMap;
	}
	
	//获取注册充电桩的初始化信息，区域、coordinate
	//获取区域信息,需要根据operatorId来进行判断
	@RequestMapping(value="/getarealist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getAreaList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//从session中获取operator
		Operator operator=(Operator) request.getSession().getAttribute("operatorCurrent");
		//目前使用设值注入的方式，将operatorId写死
		//Operator operator=new Operator();
		//operator.setOperatorId(1);
		Area areaCondition=new Area();
		areaCondition.setOperator(operator);
		//获取所有的区域信息
		List<Area> areaList=areaService.getQueryAreaByOperator(areaCondition);
		if(areaList!=null&&areaList.size()>0) {
			modelMap.put("success", true);
			modelMap.put("areaList", areaList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty areaList");
		}
		return modelMap;
	}
	
	//通过areaId获取对应的coordinate集合
	@RequestMapping(value="/getcoordinatelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getCoordinateList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int areaId=HttpServletRequestUtil.getInt(request, "areaId");
		List<Coordinate> coordinateList;
		if(areaId>0) {
			coordinateList=coordinateService.getCoordinateList(areaId);
			modelMap.put("success", true);
			modelMap.put("coordinateList", coordinateList);
		}else {//没有收到areaId
			modelMap.put("success", false);
			modelMap.put("errorMsg", "empty areaId");
		}
		return modelMap;
	}
	
	//添加pile
	@RequestMapping(value="/registerpile",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerPile(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//接收并转化相应参数，包括对象信息以及图片信息
		String pileStr=HttpServletRequestUtil.getString(request, "pileStr");
		ObjectMapper mapper=new ObjectMapper();
		Pile pile=null;
		try {
			pile=mapper.readValue(pileStr, Pile.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//处理图片的逻辑
		//使用Spring自带的CommonsMultipartFile
		CommonsMultipartFile pileImg=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=
					(MultipartHttpServletRequest)request;
			pileImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("pileImg");
			//System.out.println("*&^%$"+shopImg.getOriginalFilename());//获取用户上传照片的名称
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","上传图片不能为空");
			return modelMap;
		}
		//注册充电桩
		if(pile!=null&&pileImg!=null) {
			//通过session获取operator对象
			Operator operator=(Operator)request.getSession().getAttribute("operatorCurrent");
			//暂时使用set进行默认指定
			//Operator operator=new Operator();
			//operator.setOperatorId(1);
			pile.setOperator(operator);
			PileExecution pe=null;
			try {
				ImageHolder imageHolder=new ImageHolder(pileImg.getOriginalFilename(),pileImg.getInputStream());
				pe=pileService.addPile(pile, imageHolder);
				if(pe.getState()==PileStateEnum.CHECK.getState()) {
					logger.info("controller注册成功");
					//注册成功后,在session里面存入充电桩列表，来显示用户可以操作的充电桩有哪些
					@SuppressWarnings("unchecked")
					List<Pile> pileList=(List<Pile>)request.getSession().getAttribute("pileList");
					if(pileList==null) {
						pileList=new ArrayList<>();
					}
					pileList.add(pile);
					request.getSession().setAttribute("pileList", pileList);
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg",pe.getStateInfo());
				}
			}catch(Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg","上传图片不能为空");
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","empty pile");
			return modelMap;
		}
	}
}
