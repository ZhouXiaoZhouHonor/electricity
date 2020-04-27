package com.ze.zhou.web.phoneadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ze.zhou.entity.Notice;
import com.ze.zhou.service.NoticeService;

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
