package com.ze.zhou.web.pageadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:用于HTML页面的跳转
*/
@Controller
@RequestMapping("/page")
public class PageAdmin {
	@RequestMapping(value="/main",method=RequestMethod.GET)
	private String electricityMap() {
		return "operator/main";
	}
	
	@RequestMapping("/addpile")
	private String addPile() {
		return "operator/addpile";
	}
}
