package com.ze.zhou.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javax.swing.JOptionPane;

import com.ze.zhou.util.testelectricity.SerialTool;
import com.ze.zhou.util.testelectricity.Transfer;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/*
	author:zhouze
	@createTime:2020年5月22日
	@goal:
*/
public class PileMachine {
	private static SerialPort serialPort;
	private static List<String> list=new ArrayList<>();
	private static Runnable runnable;
	private static void getElectricityData() throws 
		UnsupportedCommOperationException, PortInUseException, 
		NoSuchPortException, TooManyListenersException, IOException {
		//获取端口,获取第一个端口号
		String portName=SerialTool.findPort().get(0);
		System.out.println("串口名称为:"+portName);
		//创建串口对象
		try {
			serialPort = SerialTool.openPort(portName, 115200); 
			SerialTool.addListener(serialPort, new SerialListener()); 
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(serialPort==null) {
			return;
		}
		try {
			SerialTool.sendToPort(serialPort, hex2byte("fa fa 09 04 00 00 00 01 30 82"));
		} catch (IOException e) {
			//出现异常关闭串口
			SerialTool.closePort(serialPort);
			e.printStackTrace();
		}
		
		runnable=new Runnable() {
			public void run() {
				while(true) {
					try {
						SerialTool.sendToPort(serialPort, hex2byte("fa fa 09 04 00 00 00 01 30 82"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						Thread.sleep(200);//休息200ms
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
					if(list.size()==1) {
						SerialTool.closePort(serialPort);
						break;
					}
				}
			}
		};
		
		
		Thread thread=new Thread(runnable);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public static List<Map<String,Float>> getElectricityResult() throws UnsupportedCommOperationException, PortInUseException, NoSuchPortException, TooManyListenersException, IOException {
		list=new ArrayList<>();
		getElectricityData();
		//分析电压、电流、频率
		List<Map<String,Float>> resultList=new ArrayList<>();
		for(String pileResult:list) {
			Map<String,Float> resultMap=new HashMap<>();
			float electricityV=Integer.parseInt(rev(pileResult.substring(8, 16)), 16);
			float electricityHz=Integer.parseInt(rev(pileResult.substring(16, 24)), 16);
			float electricityA=Integer.parseInt(rev(pileResult.substring(24, 32)), 16);
			float pileV=Integer.parseInt(rev(pileResult.substring(64, 72)), 16);
			float pileHz=Integer.parseInt(rev(pileResult.substring(72, 80)), 16);
			float pileA=Integer.parseInt(rev(pileResult.substring(80,88)), 16);
			resultMap.put("electricityV", electricityV/100);
			resultMap.put("electricityHz", electricityHz/100);
			resultMap.put("electricityA", electricityA/100);
			resultMap.put("pileV", pileV/100);
			resultMap.put("pileHz", pileHz/100);
			resultMap.put("pileA", pileA/100);
			resultList.add(resultMap);
			//System.out.println(electricityV+"/"+electricityHz+"/"+electricityA+"/"+pileV+"/"+pileHz+"/"+pileA);
		}
		//关闭串口
		SerialTool.closePort(serialPort);
		return resultList;
	}
	
	public static void main(String[] args) throws UnsupportedCommOperationException, PortInUseException, NoSuchPortException, TooManyListenersException, IOException {
		List<Map<String,Float>> list=getElectricityResult();
		System.out.println("zcxzc:"+list.get(0).get("electricityV"));
	}
	
	/**字符串转16进制
     * @param hex
     * @return
     */ 
     private static byte[] hex2byte(String hex) { 
    	 String digital = "0123456789ABCDEF"; 
    	 String hex1 = hex.replace(" ", "");
    	 char[] hex2char = hex1.toCharArray();
    	 byte[] bytes = new byte[hex1.length() / 2]; 
    	 byte temp; 
    	 for (int p = 0; p < bytes.length; p++) { 
    		 temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
    		 temp += digital.indexOf(hex2char[2 * p + 1]); 
    		 bytes[p] = (byte) (temp & 0xff);
    	 } 
    	 return bytes; 
    }
     //16进制高低位转换
	@SuppressWarnings("null")
	private static String rev(String ox){
		//System.out.println("转换前的值:"+ox);
		String[] data=new String[4];
		data[0]=ox.substring(6,8);
		data[1]=ox.substring(4,6);
		data[2]=ox.substring(2,4);
		data[3]=ox.substring(0,2);
		ox=data[0]+data[1]+data[2]+data[3];
		//System.out.println("转换后的值:"+ox);
		return ox; 
	}
     /**字节数组转16进制
     * @param b
     * @return
     */ 
     private static String printHexString(byte[] b) { 
    	 StringBuffer sbf=new StringBuffer(); 
    	 for (int i = 0; i < b.length; i++) { 
    		 String hex = Integer.toHexString(b[i] & 0xFF); 
    		 if (hex.length() == 1) { 
    			 hex = '0' + hex; 
    			 }
    		 sbf.append(hex.toUpperCase()+"  "); 
    		 } 
    	 return sbf.toString().trim();
    } 
     //计算精度1
     public static String txfloat(long a) {
		    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
		    return df.format((float)a/100);
	}
     //计算精度2
 	public static String txDu(long a) {
	    DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
	    return df.format((float)a/3200);
	}
	
	
	//创建监听内部类
	static class SerialListener implements SerialPortEventListener { 
   	 /*处理监控到的串口事件*/ 
   	 public void serialEvent(SerialPortEvent serialPortEvent) {
   		
   		 switch (serialPortEvent.getEventType()) { 
   		 case SerialPortEvent.BI: 
   			 // 10 通讯中断 
   			 JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE); 
   			 break; 
   			 case SerialPortEvent.OE: // 7 溢位（溢出）错误 
   				 break;
   			 case SerialPortEvent.FE:// 9 帧错误 
   				 break; 
   			 case SerialPortEvent.PE:// 8 奇偶校验错误 
   				 break; 
   			 case SerialPortEvent.CD: // 6 载波检测 
   				 break; 
   			 case SerialPortEvent.CTS: // 3 清除待发送数据 
   				 break;
   			 case SerialPortEvent.DSR: // 4 待发送数据准备好了 
   				 break; 
   			 case SerialPortEvent.RI: // 5 振铃指示 
   				 break; 
   			 case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
   				 break; 
   			 case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据 
   				//String time=df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()),ZoneId.of("Asia/Shanghai"))); 
   				byte[] data;//FE0400030001D5C5 
   				try {
   					data = SerialTool.readFromPort(serialPort); 
   					String test=printHexString(data);
   					//去除空格
   					String test1=test.replace(" ", "");
   					list.add(test1);
   					System.out.println(test1);
   					
   				} catch (IOException e) { 
   					e.printStackTrace();
   			} 
   				break;
   			default: 
   				break; 
   		}  
   	 } 
   	 public static List<String> getResult(){
   		 return list;
   	 }
   }
}

