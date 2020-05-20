package com.ze.zhou.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/*
	author:zhouze
	@createTime:2020年5月19日
	@goal:与RS232进行串口通信
*/
public class ConnectMachine {
	static SerialPort serialPort=null;
	@SuppressWarnings("unused")
	private static void createPort() throws UnsupportedCommOperationException, NoSuchPortException, PortInUseException, TooManyListenersException {
		//1、获取计算机的可用端口号
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> em = CommPortIdentifier
				.getPortIdentifiers();
		//实验过程中只使用第一个端口
		String portName=em.nextElement().getName();
		//2、打开串口，获取串口对象，设置串口
		//打开串口
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);//COM4是串口名字
		CommPort commPort = portIdentifier.open(portName, 2000);    //2000是打开超时时间
		serialPort = (SerialPort) commPort;
		//设置参数（包括波特率，输入/输出流控制，数据位数，停止位和齐偶校验）
		serialPort.setSerialPortParams(115200,
		SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
		SerialPort.PARITY_NONE);
		//监听串口事件
		serialPort.addEventListener(new Abc()); //Abc是实现SerialPortEventListener接口的类，具体读操作在里面进行
		// 设置当有数据到达时唤醒监听接收线程
		serialPort.notifyOnDataAvailable(true);
		// 设置当通信中断时唤醒中断线程
		serialPort.notifyOnBreakInterrupt(true);
		//    in.close(); //关闭串口
		//3、对串口进行读写操作
		
	}
	
	//字符串转16进制
	@SuppressWarnings("unused")
	private static byte[] hex2byte(String hex) {
		String digital = "0123456789ABCDEF"; 
    	String hex1 = hex.replace(" ", "");
    	char[] hex2char = hex1.toCharArray();
    	byte[] bytes = new byte[hex1.length() / 2]; 
    	byte temp; 
    	for(int p=0;p<bytes.length;p++) {
    		temp = (byte) (digital.indexOf(hex2char[2 * p]) * 16);
    		temp += digital.indexOf(hex2char[2 * p + 1]); 
    		bytes[p] = (byte) (temp&0xff);
    	} 
    	System.out.println("axasca");
    	return bytes; 
    } 
	
	public static void main(String[]args) throws UnsupportedCommOperationException, NoSuchPortException, PortInUseException, TooManyListenersException, IOException {
		createPort();
		OutputStream out = serialPort.getOutputStream();
		out.write(hex2byte("fa fa 01 03 00 00 00 01 84 0a")); //byte[] data;
		out.flush();
		out.close(); 
	}
}
class Abc implements SerialPortEventListener {
    public void serialEvent(SerialPortEvent arg0) {
        //对以下内容进行判断并操作
        /*
	    BI -通讯中断
		CD -载波检测
		CTS -清除发送
		DATA_AVAILABLE -有数据到达
		DSR -数据设备准备好
		FE -帧错误
		OE -溢位错误
		OUTPUT_BUFFER_EMPTY -输出缓冲区已清空
		PE -奇偶校验错
	    RI -　振铃指示
        */
        //switch多个，if单个
    	InputStream in = null;
        byte[] bytes = null;
        if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                
                in = ConnectMachine.serialPort.getInputStream();

                int bufflenth = in.available();
                while (bufflenth != 0) {
                    // 初始化byte数组为buffer中数据的长度
                    bytes = new byte[bufflenth];
                    in.read(bytes);
                    System.out.println(new String(bytes));
                    bufflenth = in.available();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }    
}