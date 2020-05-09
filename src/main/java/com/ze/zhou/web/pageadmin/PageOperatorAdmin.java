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
public class PageOperatorAdmin {
	//网站首页
	@RequestMapping(value="/main",method=RequestMethod.GET)
	private String electricityMap() {
		return "operator/main";
	}
	//注册充电桩页面
	@RequestMapping("/addpile")
	private String addPile() {
		return "operator/addpile";
	}
	
	//充电桩检测页面
	@RequestMapping(value="/pileelectricity",method=RequestMethod.GET)
	private String pileElectricity() {
		return "operator/pileelectricity";
	}
}
