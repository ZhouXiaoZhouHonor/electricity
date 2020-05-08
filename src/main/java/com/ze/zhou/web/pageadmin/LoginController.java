package com.ze.zhou.web.pageadmin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ze.zhou.dto.OperatorExecution;
import com.ze.zhou.entity.Operator;
import com.ze.zhou.enums.OperatorStateEnum;
import com.ze.zhou.service.OperatorService;
import com.ze.zhou.util.CodeUtil;
import com.ze.zhou.util.HttpServletRequestUtil;
import com.ze.zhou.web.superadmin.AreaSuperadminController;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年5月6日
	@goal:管理员登录界面
*/
@Controller
@RequestMapping("/login")
public class LoginController {
	
	Logger logger=(Logger) LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private OperatorService operatorService;
	
	@RequestMapping(value="operatorlogin",method=RequestMethod.GET)
	private String operatorLogin() {
		return "login/operatorlogin";
	}
	
	@RequestMapping(value="/operatorcheck",method=RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> operatorCheck(HttpServletRequest request){
		Map<String,Object> modelMap=new HashMap<>();
		//判断验证码输入是否正确
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		} 
		//接收json数据
		String operatorStr=HttpServletRequestUtil.getString(request, "operatorStr");
		ObjectMapper mapper=new ObjectMapper();
		Operator operator=null;
		try {
			operator=mapper.readValue(operatorStr, Operator.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		//TODO 进行账号验证
		OperatorExecution oe=operatorService.operatorCheck(operator);
		if(oe.getState()==OperatorStateEnum.CHECK_SUCCESS.getState()) {
			modelMap.put("success", true);
			//成功后，需要将用户放入session中保存
			request.getSession().setAttribute("operatorCurrent", oe.getOperator());
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", oe.getStateInfo());
		}
		return modelMap;
	}
}
