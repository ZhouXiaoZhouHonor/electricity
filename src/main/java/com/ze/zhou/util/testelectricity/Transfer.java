package com.ze.zhou.util.testelectricity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Transfer {
	//保留两位精度
	public static String txfloat(long a) {
	    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
	    return df.format((float)a/100);
	}
	public static String txDu(long a) {
	    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
	    return df.format((float)a/3200);
	}
	//高低位转换
	static String rev(String ox){
		  byte b[] = ox.getBytes();
		  byte result[] = new byte[b.length];
			for (int i= b.length-1, j=0; i>=0;i=i-2,j=j+2){
			result[j]= b[i-1];
			result[j+1]= b[i];
			}
		  return new String(result);
		 }
	//分隔数据位
	 public static List<String> fg(String ox) {
		 char[] ch=null;
		 ch=ox.toCharArray();
		 List<String> list = new ArrayList<String>(); 
		 //System.out.println(ch);
		 String V=rev(new String(ch,8,8));
		 String Hz=rev(new String(ch,16,8));
		 String A=rev(new String(ch,24,8));
		 String YW=rev(new String(ch,32,8));
		 String WW=rev(new String(ch,40,8));
		 String yougong=rev(new String(ch,48,8));
		 String wugong=rev(new String(ch,56,8));
		 String zhuangV=rev(new String(ch,64,8));
		 String zhuangHz=rev(new String(ch,72,8));
		 String zhuangA=rev(new String(ch,80,8));
		 String zhuangYW=rev(new String(ch,88,8));
		 String zhuangyougong=rev(new String(ch,96,8));
		 String zhuangwugong=rev(new String(ch,104,8));
//		 System.out.println(V);
//		 System.out.println(Hz);
//		 System.out.println(A);
//		 System.out.println(YW);
//		 System.out.println(WW);
//		 System.out.println(yougong);
//		 System.out.println(wugong);
//		 System.out.println(zhuangV);
//		 System.out.println(zhuangHz);
//		 System.out.println(zhuangA);
//		 System.out.println(zhuangYW);
//		 System.out.println(zhuangyougong);
//		 System.out.println(zhuangwugong);
		 list.add(V);
		 list.add(Hz);
		 list.add(A);
		 list.add(YW);
		 list.add(WW);
		 list.add(yougong);
		 list.add(wugong);
		 list.add(zhuangV);
		 list.add(zhuangHz);
		 list.add(zhuangA);
		 list.add(zhuangYW);
		 list.add(zhuangyougong);
		 list.add(zhuangwugong);
		 //System.out.println(list);
		return list;
		 
	 }

}