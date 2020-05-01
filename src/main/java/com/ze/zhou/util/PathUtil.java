package com.ze.zhou.util;

//主要提供路径

public class PathUtil {
	//file.separator这个代表系统目录中的间隔符，说白了就是斜线，不过有时候需要双线，有时候是单线，你用这个静态变量就解决兼容问题了。
	//其实是不需要的
	//private static String seperator=System.getProperty("file.separator");//获取运行系统上的文件间隔符,windows下是“\”
	
	/*
	 * @return 返回项目图片的根路径
	 * */
	public static String getImgBasePath() {
		String os=System.getProperty("os.name");//获取系统名称。Windows 10
		
		String basePath="";
		
		if(os.toLowerCase().startsWith("win")) {//将系统名称转换为小写字符串，进行字符串比较，判断是否为Windows系统
			basePath="E:/Users/electricity_image";//是Windows系统，使用Windows的文件路径
		}else {//其他系统的路径
			basePath="/home/electricity_image/image";
		}
		//basePath=basePath.replace("/", seperator);//为了适应不同的系统，而将默认的文件间隔符进行替换
		
		return basePath;
	}
	
	/*
	 * @return 依据不同的业务项目需要，返回充电桩图片的子路径*/
	public static String getPileImagePath(long pileId) {//获取店铺图片路径
		String imagePath="/electricity_upload/pile"+pileId+"/";
		//return imagePath.replace("/", seperator);
		return imagePath;//不应该用win的分隔符
	}
	
	/*
	 * @return 依据不同的业务项目需要，返回管理员的子路径*/
	public static String getOperatorImagePath(long operatorId) {//获取店铺图片路径
		String imagePath="/electricity_upload/operator"+operatorId+"/";
		//return imagePath.replace("/", seperator);
		return imagePath;//不应该用win的分隔符
	}
	
	/*
	 * @return 依据不同的业务项目需要，返回反馈问题的子路径*/
	public static String getProblemImagePath(long problemId) {//获取店铺图片路径
		String imagePath="/electricity_upload/problem"+problemId+"/";
		//return imagePath.replace("/", seperator);
		return imagePath;//不应该用win的分隔符
	}
	
	/*
	 * @return 依据不同的业务项目需要，返回手机用户图片的子路径*/
	public static String getPhoneUserImagePath(long userId) {//获取店铺图片路径
		String imagePath="/electricity_upload/phoneuser"+userId+"/";
		//return imagePath.replace("/", seperator);
		return imagePath;//不应该用win的分隔符
	}
	
	public static void main(String[]args) {
		//System.out.println(PathUtil.seperator);
		//System.out.println(PathUtil.getImgBasePath());
	}
	
}
