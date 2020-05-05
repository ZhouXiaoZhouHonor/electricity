package com.ze.zhou.web.superadmin;

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

import com.ze.zhou.dto.NoticeExecution;
import com.ze.zhou.entity.Area;
import com.ze.zhou.entity.Notice;
import com.ze.zhou.service.AreaService;
import com.ze.zhou.service.NoticeService;
import com.ze.zhou.util.HttpServletRequestUtil;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年3月31日
	@goal:
*/
@Controller
@RequestMapping("/superadmin")
public class AreaSuperadminController {
	Logger logger=(Logger) LoggerFactory.getLogger(AreaSuperadminController.class);
	@Autowired
	private AreaService areaService;
	@Autowired
	private NoticeService noticeService;
	
	//获取公告信息
	@SuppressWarnings("null")
	@RequestMapping(value="/getnoticelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getNoticeList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		int pageIndex=HttpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize=HttpServletRequestUtil.getInt(request, "pageSize");
		NoticeExecution ne=noticeService.getQueryNotice(pageIndex, pageSize);
		if(ne!=null) {
			modelMap.put("success", true);
			modelMap.put("noticeList", ne.getNoticeList());
			modelMap.put("count", ne.getCount());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", ne.getStateInfo());
		}
		return modelMap;
	}
	
	//获取区域信息
	@RequestMapping(value="/getarealist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getAreaList(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		List<Area> areaList=areaService.getQueryArea();
		logger.debug("zcxcz");
		modelMap.put("success", true);
		modelMap.put("areaList", areaList);
		return modelMap;
	}
}
