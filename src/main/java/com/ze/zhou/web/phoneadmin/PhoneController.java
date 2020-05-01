package com.ze.zhou.web.phoneadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ze.zhou.entity.Coordinate;
import com.ze.zhou.entity.Notice;
import com.ze.zhou.service.CoordinateService;
import com.ze.zhou.service.NoticeService;
import com.ze.zhou.service.PileService;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
@Controller
@RequestMapping("/phone")
public class PhoneController {
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private CoordinateService coordinateService;
	
	//获取充电桩
	@RequestMapping(value="/getcoordinatelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getCoordinateList(){
		Map<String,Object> modelMap=new HashMap<>();
		List<Coordinate> coordinateList=coordinateService.getAllCoordinateList();
		if(coordinateList!=null&&coordinateList.size()>0) {
			modelMap.put("success", true);
			modelMap.put("coordinateList", coordinateList);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty coordinateList");
		}
		return modelMap;
	}
	
	//获取轮播图
	@RequestMapping(value="/getnoticelist",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getNoticeList(){
		Map<String,Object> modelMap=new HashMap<>();
		List<Notice> noticeList=noticeService.getQueryNotice();
		if(noticeList.size()>0&&noticeList!=null) {
			modelMap.put("noticeList", noticeList);
			modelMap.put("success", true);
		}else {
			modelMap.put("errMsg", "empty noticeList");
			modelMap.put("success", false);
		}
		return modelMap;
	}
}
