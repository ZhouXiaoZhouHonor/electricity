package com.ze.zhou.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;

import com.ze.zhou.entity.Pile;
import com.ze.zhou.entity.PileElectricity;
import com.ze.zhou.entity.VAError;

import ch.qos.logback.classic.Logger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/*
	author:zhouze
	@createTime:2020年5月17日
	@goal:打印报表
*/
public class ElectricityReport {
	Logger logger=(Logger) LoggerFactory.getLogger(ElectricityReport.class);
	private static Configuration configuration;
	public static void main(String[]args) {
		List<PileElectricity> list=new ArrayList<>();
		Pile pile=new Pile();
		pile.setPileId(23L);
		PileElectricity pe=new PileElectricity();
		pe.setPile(pile);
		list.add(pe);
		String name=createPileReport(list);
		System.out.println("文件名称为:"+name);
		/*
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
		*/
	}
	//将报表生成，存入指定硬盘中并返回存储文件路径
	public static String createPileReport(List<PileElectricity> pileElectricityList) {
		Calendar now = Calendar.getInstance();  
		String dest="";
		Map<String,Object> dataMap = new HashMap<String, Object>();//数据类型在不知道的情况下，使用Object进行代替；用H进行HashMap进行存储
		int year=now.get(Calendar.YEAR);
		dataMap.put("year", year);
		int month=now.get(Calendar.MONTH)+1;
		dataMap.put("month", month);
		int day=now.get(Calendar.DAY_OF_MONTH);
		dataMap.put("day", day);
		String reportNumber=""+year+month+day;
		dataMap.put("certificateNumber", reportNumber);
		/*
		String v="";
		dataMap.put("v", "@");
		String a="";
		dataMap.put("a", "@");
		String vAError="";
		dataMap.put("vAError", "@");
		*/
		String workingError="";
		dataMap.put("workingError", "@");
		
		List<VAError> EList=new ArrayList<VAError>();
    	for(int i=0;i<pileElectricityList.size();i++) {
    		VAError elec=new VAError();
    		elec.setV(pileElectricityList.get(i).getElectricityV());
    		elec.setA(pileElectricityList.get(i).getElectricityI());
			elec.setErrorResult(0.03f);
    		EList.add(elec);
    	}
    	dataMap.put("electricityList", EList);
		
		String viewError="";
		dataMap.put("viewError", viewError+"@");
		String eBefore="";
		String eAfter="";
		String eResult="";
		String eStand="";
		String eError="";
		dataMap.put("eBefore", "@");
		dataMap.put("eAfter", "@");
		dataMap.put("eResult", "@");
		dataMap.put("eStand", "@");
		dataMap.put("eError", "@");
		
		String moneyError="";
		String viewMoney="";
		String energy="";
		String price="";
		String money="";
		String moneyErrors="";
		dataMap.put("moneyError", "@");
		dataMap.put("viewMoney", "@");
		dataMap.put("energy", "@");
		dataMap.put("price", "@");
		dataMap.put("money", "@");
		dataMap.put("moneyErrors", "@");
		
		String timeError="";
		String time="";
		String timeStand="";
		String timeErrorResult;
		dataMap.put("timeError", "@");
		dataMap.put("time", "@");
		dataMap.put("timeStand", "@");
		dataMap.put("timeErrorResult", "@");
		
		//Configuration 用于读取ftl文件
        configuration = new Configuration(new Version("2.3.23"));
        configuration.setDefaultEncoding("utf-8");
		
        //指定路径的第二种方式，我的路径是C：/a.ftl
        try {
			configuration.setDirectoryForTemplateLoading(new File("E:/Users/16078/eclipse-workspace/zhou/src/main/resources/report"));
			//输出文档路径及名称
			String path=PathUtil.getImgBasePath();
			dest=PathUtil.getElectricityReportPath(pileElectricityList.get(0).getPile().getPileId());
			String filePath=path+dest+ImageUtil.getRandomFileName()+".doc";
			//System.out.println("替换前:"+filePath);
			String filePath1=filePath.replaceAll("/", "\\\\");
			//System.out.println("替换后:"+filePath1);
            File outFile = new File(filePath1);
            //首先创建文件夹
            if(!outFile.getParentFile().exists()) {
            	outFile.getParentFile().mkdirs();
            }
            //创建文件
            if(!outFile.exists()) {//如果文件不存在则创建该文件
            	outFile.createNewFile();
            }
            //以utf-8的编码读取ftl文件
            Template template = configuration.getTemplate("electricity.ftl", "utf-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            
            template.process(dataMap, out);//将数据写入报告中
            out.flush();
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        //返回绝对路径名称
		return dest+ImageUtil.getRandomFileName()+".doc";
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
