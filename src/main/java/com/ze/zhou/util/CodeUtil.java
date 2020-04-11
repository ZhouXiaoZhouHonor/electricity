package com.ze.zhou.util;

import javax.servlet.http.HttpServletRequest;

/*
	author:zhouze
	@createTime:2020年1月22日
	@goal:获取图片中的验证码并比较输入中的验证码是否一致
*/
public class CodeUtil {
	//Logger logger=(Logger) LoggerFactory.getLogger(CodeUtil.class);
	public static boolean checkVerifyCode(HttpServletRequest req) {
		//获取图片生成的验证码
		String verifyCodeExpected=(String)req.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		//获取文本框中输入的验证码
		String verifyCodeActual=HttpServletRequestUtil.getString(req,"verifyCodeActual");//传入的应该是request和需要获取文本框值中的name
		System.out.println(verifyCodeActual+"$&&"+verifyCodeExpected);
		//若二者不一致则返回false
		if(verifyCodeActual==null||!verifyCodeExpected.equals(verifyCodeActual)) {
			return false;
		}
		return true;
	}
}
