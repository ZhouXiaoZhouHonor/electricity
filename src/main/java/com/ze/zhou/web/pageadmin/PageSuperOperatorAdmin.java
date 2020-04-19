package com.ze.zhou.web.pageadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
	author:zhouze
	@createTime:2020年4月17日
	@goal:
*/
@Controller
@RequestMapping("/pagesuper")
public class PageSuperOperatorAdmin {
	//超级管理员主界面
	@RequestMapping(value="/main",method=RequestMethod.GET)
	private String main() {
		return "superoperator/main";
	}
	//超级管理员添加充电桩页面
	@RequestMapping("/addarea")
	private String addArea() {
		return "superoperator/addarea";
	}
}
