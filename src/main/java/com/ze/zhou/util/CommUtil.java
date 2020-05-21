package com.ze.zhou.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/*
	author:zhouze
	@createTime:2020年5月20日
	@goal:
*/
public class CommUtil implements SerialPortEventListener{
	 InputStream inputStream; // 从串口来的输入流  
	    OutputStream outputStream;// 向串口输出的流  
	    SerialPort serialPort; // 串口的引用  
	    CommPortIdentifier portId;
	  
	    public CommUtil(Enumeration portList, String name) { 
	        while (portList.hasMoreElements()) {  
	            CommPortIdentifier temp = (CommPortIdentifier) portList.nextElement();  
	            if (temp.getPortType() == CommPortIdentifier.PORT_SERIAL) {// 判断如果端口类型是串口  
	                if (temp.getName().equals(name)) { // 判断如果端口已经启动就连接  
	                    portId = temp;  
	                }  
	            }  
	        }  
	        try {  
	            serialPort = (SerialPort) portId.open("COM5",2000);  
	        } catch (PortInUseException e) {  
	        	System.out.println("出错了");
	        } 
	        
	        try {  
	            serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8, // 设置串口读写参数  
	                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	            System.out.println("设置端口参数");
	        } catch (UnsupportedCommOperationException e) { 
	        	System.out.println("设置端口参数失败");
	        }
	        
	        try {  
	            inputStream = serialPort.getInputStream();  
	            outputStream = serialPort.getOutputStream();
	            System.out.println("创建了数据流");
	        } catch (IOException e) {
	        	System.out.println("创建数据流失败");
	        }  
	        try {  
	            serialPort.addEventListener(this); //给当前串口天加一个监听器  
	        } catch (TooManyListenersException e){
	        }  
	        serialPort.notifyOnDataAvailable(true); //当有数据时通知  
	        try {
	        	serialPort.enableReceiveTimeout(2000);
			} catch (UnsupportedCommOperationException e) {
				System.out.println("设置接收时间");
				e.printStackTrace();
			}
	    }  
	  
	    public void serialEvent(SerialPortEvent event) {
	    	System.out.println("时间值为:"+event.getEventType());
	        switch (event.getEventType()) {  
	        case SerialPortEvent.BI: System.out.println("接收数据成功1"); break;  
	        case SerialPortEvent.OE: System.out.println("接收数据成功2"); break;  
	        case SerialPortEvent.FE: System.out.println("接收数据成功3"); break;  
	        case SerialPortEvent.PE: System.out.println("接收数据成功4"); break;  
	        case SerialPortEvent.CD: System.out.println("接收数据成功5"); break;  
	        case SerialPortEvent.CTS: System.out.println("接收数据成功6"); break;  
	        case SerialPortEvent.DSR: System.out.println("接收数据成功7"); break;  
	        case SerialPortEvent.RI: System.out.println("接收数据成功8"); break;  
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY: System.out.println("接收数据成功"); break;  
	        case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据,并且给串口返回数据  
	            byte[] readBuffer = new byte[100];
	            System.out.println("接收数据成功");
	            try {  
	                while (inputStream.available() > 0) {  
	                    System.out.println(inputStream.available());  
	                    int numBytes = inputStream.read(readBuffer);  
	                    System.out.println(numBytes);  
	                }  
	                System.out.println(new String(readBuffer).trim());  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            break;  
	        }  
	    }  
	    
	    public void send(String content) throws DecoderException{
	        try {  
	        	outputStream.write(hex2byte(content));  
	            outputStream.flush();
	            System.out.println(outputStream.toString());
	            System.out.println("数据写入成功");
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    private byte[] hex2byte(String hex) { 
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
	    
	    public void ClosePort() {
	    	try {
	    		if(inputStream!=null) {
		    		inputStream.close();
		    	}
		    	if(outputStream!=null) {
		    		outputStream.close();
		    	}
		    	System.out.println("关闭数据流成功");
	    	}catch(Exception e) {
	    		System.out.println("关闭数据流没有成功");
	    		e.printStackTrace();
	    	}
	        if (serialPort != null){  
	          serialPort.close(); 
	          System.out.println("关闭se成功");
	        }  
	    } 
	    public static void main(String[] args) throws InterruptedException,DecoderException {
	    	Enumeration portList = CommPortIdentifier.getPortIdentifiers(); //得到当前连接上的端口  
	        System.out.println("开始");
	        CommUtil comm3 = new CommUtil(portList,"COM5");
	        System.out.println("中间");
	        int i = 0;  
	        while(i<1)  
	        {  
	            Thread.sleep(3000);  
	            System.out.println("即将发送报文");
	            comm3.send("fa fa 09 04 00 00 00 01 30 82");  
	            i++;
	        }  
	        comm3.ClosePort();  
	        System.out.println("结束");
	    }  	    
}
