package com.ze.zhou.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
public class HttpServletRequestUtil {
	//字符串转换成整型
		public static int getInt(HttpServletRequest request,String key) {
			try {
				return Integer.decode(request.getParameter(key));
			}catch(Exception e) {
				return -1;
			}
		}
		//字符串转换成长整型
		public static Long getLong(HttpServletRequest request,String key) {
			try {
				return Long.valueOf(request.getParameter(key));
			}catch(Exception e) {
				return -1L;
			}
		}
		//字符串转换成双浮点型
		public static Double getDouble(HttpServletRequest request,String key) {
			try {
				return Double.valueOf(request.getParameter(key));
			}catch(Exception e) {
				return -1D;
			}
		}
		//字符串转换成Boolean型
		public static Boolean getBoolean(HttpServletRequest request,String key) {
			try {
				return Boolean.valueOf(request.getParameter(key));
			}catch(Exception e) {
				return false;
			}
		}
		//转换成String类型
		public static String getString(HttpServletRequest request,String key) {
			try {
				String result=request.getParameter(key);
				if(result!=null) {
					result=result.trim();
				}
				if("".equals(result)) {
					result=null;
				}
				return result;
			}catch(Exception e) {
				return null;
			}
		}
		//字符串转换成Date类型，字符串也是毫秒数
		public static Date getDate(HttpServletRequest request,String key) {
			DateFormat result=new SimpleDateFormat();
			try {
				Long dateChar=Long.valueOf(request.getParameter(key));
				Date date=new Date();
				if(dateChar!=null) {
					date.setTime(dateChar);
				}else {
					dateChar=null;
				}
				return date;
			}catch(Exception e) {
				return null;
			}
		}
		
		public static void main(String[]args) {
			//HttpServletRequest request=new HttpServletRequest();
		/*
		 * long milliSecond = 1570650412089L; Date date = new Date();
		 * date.setTime(milliSecond); System.out.println(date);
		 */
		}
}
