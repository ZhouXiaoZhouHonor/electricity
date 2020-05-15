package com.ze.zhou.web.operatoradmin;

import java.io.IOException;
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
import com.ze.zhou.dto.AreaExecution;
import com.ze.zhou.dto.CoordinateExecution;
import com.ze.zhou.dto.NoticeExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Notice;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.enums.AreaStateEnum;
import com.ze.zhou.enums.CoordinateStateEnum;
import com.ze.zhou.enums.NoticeStateEnum;
import com.ze.zhou.service.AreaService;
import com.ze.zhou.service.CoordinateService;
import com.ze.zhou.service.NoticeService;
import com.ze.zhou.service.OperatorService;
import com.ze.zhou.util.CodeUtil;
import com.ze.zhou.util.HttpServletRequestUtil;
import com.ze.zhou.util.ImageHolder;
import com.ze.zhou.web.superadmin.AreaSuperadminController;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年4月17日
	@goal:
*/
@Controller
@RequestMapping("/superoperator")
public class SuperOperatorController {
	Logger logger=(Logger) LoggerFactory.getLogger(SuperOperatorController.class);
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private CoordinateService coordinateService;
	@Autowired
	private NoticeService noticeService;
	
	//添加公告信息
	@RequestMapping(value="/registernotice",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerNotice(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}
		//获取json字符串
		Notice notice=null;
		ObjectMapper mapper=new ObjectMapper();
		String noticeStr=HttpServletRequestUtil.getString(request, "noticeStr");
		if(noticeStr!=null) {
			try {
				notice=mapper.readValue(noticeStr, Notice.class);
			}catch(Exception e) {
				modelMap.put("success", true);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}
		}
		//处理图片的逻辑
		//使用Spring自带的CommonsMultipartFile
		CommonsMultipartFile noticeImg=null;
		CommonsMultipartFile noticeLink=null;
		CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest=
					(MultipartHttpServletRequest)request;
			noticeImg=(CommonsMultipartFile)multipartHttpServletRequest.getFile("noticeImg");
			noticeLink=(CommonsMultipartFile)multipartHttpServletRequest.getFile("noticeLink");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","上传图片不能为空");
			return modelMap;
		}
		ImageHolder imageHolder1=null;
		ImageHolder imageHolder2=null;
		if(notice!=null) {
			try {
				imageHolder1=new ImageHolder(noticeImg.getOriginalFilename(),noticeImg.getInputStream());
				imageHolder2=new ImageHolder(noticeLink.getOriginalFilename(),noticeLink.getInputStream());
				logger.debug("图片1:"+imageHolder1.getImageName()+"/"+imageHolder1.getImage());
				logger.debug("图片2:"+imageHolder2.getImageName()+"/"+imageHolder2.getImage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg","上传图片不能为空");
				return modelMap;
			}
		}
		NoticeExecution ne=noticeService.addNotice(notice, imageHolder1, imageHolder2);
		if(ne.getState()==NoticeStateEnum.SUCCESS.getState()) {
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "添加公告信息失败");
		}
		return modelMap;
	}
	
	//更改公告信息状态
	@RequestMapping(value="/modifynoticestate",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> modifyNoticeState(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int noticeEnableStatus=HttpServletRequestUtil.getInt(request, "enableStatus");
		int noticeId=HttpServletRequestUtil.getInt(request, "noticeId");
		if(noticeId>0) {
			Notice notice=new Notice();
			notice.setNoticeId(noticeId);
			notice.setNoticeEnableStatus(noticeEnableStatus);
			NoticeExecution ne=noticeService.changeNotice(notice);
			if(ne.getState()==NoticeStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ne.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty noticeId");
		}
		return modelMap;
	}
	
	
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
