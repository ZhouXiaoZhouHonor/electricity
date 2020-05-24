package com.ze.zhou.util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
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
	//检查串口是否存在,并返回串口名称
	/*
	 * public static Boolean checkPort() {
	 * 
	 * return true; }
	 */
	public static void A() throws UnsupportedCommOperationException, PortInUseException, NoSuchPortException, TooManyListenersException, IOException {
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
		//设置定时器，每隔2秒发送一次指令，发送10次
		TimerTask timerTask=new TimerTask() {
			int count;
			public void run() {
				try {
					SerialTool.sendToPort(serialPort, hex2byte("fa fa 09 04 00 00 00 01 30 82"));
					count++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//拿到10次数据后，定时弃停止调度，但串口仍然连接着
				if(count==10) {
					this.cancel();
				}
			}
		};
		
		Timer timer=new Timer();
		timer.schedule(timerTask, 0, 3000);
	}
	
	public static void main(String[] args) {
		try {
			A();
		} catch (UnsupportedCommOperationException | PortInUseException | NoSuchPortException
				| TooManyListenersException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
   	 /**
        * 处理监控到的串口事件
        */ 
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
   					System.out.println(test1);
   					
   				} catch (IOException e) { 
   					e.printStackTrace();
   			} 
   				break;
   			default: break; 
   		}  
   	 } 
   }
}

