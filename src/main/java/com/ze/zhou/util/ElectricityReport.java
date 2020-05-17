package com.ze.zhou.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ze.zhou.entity.PileElectricity;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/*
	author:zhouze
	@createTime:2020年5月17日
	@goal:打印报表
*/
public class ElectricityReport {
	
	private static Configuration configuration;
	public static void main(String[]args) {
		ElectricityReport d=new ElectricityReport();
		List<Float> list=new ArrayList<Float>();
		//电的显示误差
		list.add((float) 0);
		list.add((float) 3);
		list.add((float) 2.9);
		//金额误差
		list.add((float)3.0);
		list.add((float)1);
		//时钟误差
		list.add((float)13.6);
		list.add((float)14);
		//电压，电流，工作误差
		d.test(list);
	}
	
	public void test(List<Float> list){
        Map<String,Object> dataMap = new HashMap<String, Object>();//数据类型在不知道的情况下，使用Object进行代替；用H进行HashMap进行存储
       // Map<String,List> dataMapEI = new HashMap<String, List>();
        try {
        	//电压，电流，工作误差
        	//循环打印
        	List<PileElectricity> EList=new ArrayList<PileElectricity>();
        	for(int i=0;i<3;i++) {
        		PileElectricity elec=new PileElectricity();
        		elec.setElectricityV(223f);
        		elec.setElectricityI(2.3f);
				/* elec.setWe("0.01"); */
        		EList.add(elec);
        	}
        	dataMap.put("electricityList", EList);
        	
            //充电检测前,${EB}
            dataMap.put("EB",list.get(0));
            //充电检测后,${EA}
            dataMap.put("EA",list.get(1));
            //前后差结果,${ER}
            float result=(Float)list.get(1)-(Float) list.get(0);
            dataMap.put("ER",result);
            //标准显示值,${EV}
            dataMap.put("EV",list.get(2));
            //误差,${EE}
            float error=Math.abs(list.get(2)-result)/100;
            dataMap.put("EE",error);
            
            //金额显示值
            dataMap.put("VM", list.get(3));
            //单价
            dataMap.put("EP", list.get(4));
            //金额
            float money=list.get(2)*list.get(4);
            dataMap.put("EM",money);
            //金额误差
            float moneyError=Math.abs(money-list.get(3));
            dataMap.put("ME", moneyError);
            
            //时钟误差
            dataMap.put("VT", list.get(5));
            dataMap.put("ST", list.get(6));
            float timeError=Math.abs(list.get(5)-list.get(6));
            dataMap.put("VE", timeError);
            
            //Configuration 用于读取ftl文件
            configuration = new Configuration(new Version("2.3.23"));
            configuration.setDefaultEncoding("utf-8");
 
            /**
             * 以下是两种指定ftl文件所在目录路径的方式，注意这两种方式都是
             * 指定ftl文件所在目录的路径，而不是ftl文件的路径
             */
            //指定路径的第一种方式（根据某个类的相对路径指定）
            //configuration.setClassForTemplateLoading(this.getClass(), "");
 
            //指定路径的第二种方式，我的路径是C：/a.ftl
            configuration.setDirectoryForTemplateLoading(new File("E:/Users/16078/eclipse-workspace/JavaAPI/src/Reports/ElectricityFiles"));
 
            //输出文档路径及名称
            File outFile = new File("E:/Users/16078/eclipse-workspace/JavaAPI/src/Reports/ElectricityFiles/zhou.doc");
            //以utf-8的编码读取ftl文件
            Template template = configuration.getTemplate("electricityReport.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            //template.process(dataMapEI, out);
            
            template.process(dataMap, out);//将数据写入报告中
            out.flush();
            out.close();
            System.out.println("success!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
