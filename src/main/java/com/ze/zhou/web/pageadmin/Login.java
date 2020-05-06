package com.ze.zhou.web.pageadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
	author:zhouze
	@createTime:2020年5月6日
	@goal:登录界面
*/
@Controller
@RequestMapping("/login")
public class Login {
	@RequestMapping(value="operatorlogin",method=RequestMethod.GET)
	private String operatorLogin() {
		return "login/operatorlogin";
	}
}
