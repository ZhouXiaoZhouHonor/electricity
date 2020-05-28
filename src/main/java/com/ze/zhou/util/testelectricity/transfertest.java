package com.ze.zhou.util.testelectricity;

import java.text.DecimalFormat;
import java.util.List;


	 public class transfertest { 
		 private Transfer fg=new Transfer();
		 public static String txfloat(long a) {
			    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
			    return df.format((float)a/100);
			}
		public static String txDu(long a) {
			    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
			    return df.format((float)a/3200);
			}
		 
	     public static void main(String[] args) {  
	         //Scanner in = new Scanner(System.in);  
	         String hex_num = "090400343B5D0000861300003100000000000000C8000000932A18000000DD003E5D0000851300000200000000000000FBFFFF04CE8240029069B5";
	         List<String> list=Transfer.fg(hex_num);
	         for(int i = 0 ; i < list.size() ; i++) {
	        	 if(i==6 || i==7){
	        		 long dec_num = Long.parseLong(list.get(i), 16);
	        		 String dec_num1=txDu(dec_num);
	        		 System.out.println(dec_num1);
	        		 //receiveArea.append(time+" ["+serialPort.getName().split("/")[3]+"] : "+ dec_num1+"\r\n");
	            }
	        	 else {
		        	 long dec_num = Long.parseLong(list.get(i), 16);
		        	 String dec_num1=txfloat(dec_num);
		        	 //receiveArea.append(time+" ["+serialPort.getName().split("/")[3]+"] : "+ dec_num1+"\r\n");
	        	 }  

	     }
	 }}