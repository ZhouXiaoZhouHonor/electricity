package com.ze.zhou.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ch.qos.logback.classic.Logger;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/*
处理缩略图并返回新生成图片的相对子路径
@param thumbnail
@param targetAddr
@return
*/
/*CommonsMultipartFile thumbnail的缺点在于无法进行实例化，
 * 只能在上传文件的时候才能实例化出该对象。
 * 将File转换成CommonsMultipartFile thumbnail也是不现实的，
 * 但CommonsMultipartFile thumbnail中有transfer方法将本身转换成File。
 * 接下来就是将CommonsMultipartFile改成File.
 * 在使用是需要进行转换
 */

public class ImageUtil {

	private static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");//创建文本格式，时间的文本格式
	private static final Random r=new Random();//创建随机数生成对象
	private static Logger logger=(Logger) LoggerFactory.getLogger(ImageUtil.class);//创建日志对象
	
	/*处理图片的尺寸不是全都相同的，可以将尺寸作为参数进行调用，参数的值可以写成枚举类*/
	//处理缩略图，用户传递过来的图片。充电桩的展示图
	//第一个参数:接收上传的文件;第二个参数:存放的目录
	public static String generateThumbnail(ImageHolder thumbnail,String targetAddr,ImageSize imageSize) {
		String realFileName=getRandomFileName();//获得图片名称，随机生成，使得图片的名字不会重复
		String extension=getFileExtension(thumbnail.getImageName());//获取图片的文件扩展名(png、jpg等)
		makeDirPath(targetAddr);//创建目标路径上所涉及的目录
		String relativeAddr=targetAddr+realFileName+extension;//图片的相对路径,包含文件夹和图片的名字以及文件的扩展名
		logger.debug("current relativeAddr is:"+relativeAddr);//日志信息的添加
		File dest=new File(PathUtil.getImgBasePath()+relativeAddr);//生成文件对象。这个就是绝对路径，包含盘符、目录、文件名称、扩展名。也就是目标文件需要存放在系统的哪一个位置
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		try {/*.watermark(Positions.BOTTOM_CENTER,ImageIO.read(new File(basePath+"timg.jpg")).outputQuality(0.8f),0.25f)不打水印了*/
			Thumbnails.of(thumbnail.getImage())
			.size(imageSize.getWidth(), imageSize.getHeight())
			.keepAspectRatio(false)
			.toFile(dest);
		}catch(IOException e) {
			e.printStackTrace();
		}
		logger.debug("图片的宽度:"+imageSize.getWidth()+"/高度:"+imageSize.getHeight());
		return relativeAddr;//返回图片的路径，因为shop中的有一个字段用来存放图片地址。返回的是相对路径，适配不同的系统上
	}
	
	//处理图片详情图(暂时不用了)
	public static String generateNormalImg(ImageHolder thumbnail,String targetAddr) {
		String realFileName=getRandomFileName();//获得图片名称，随机生成，使得图片的名字不会重复
		String extension=getFileExtension(thumbnail.getImageName());//获取图片的文件扩展名(png、jpg等)
		makeDirPath(targetAddr);//创建目标路径上所涉及的目录
		String relativeAddr=targetAddr+realFileName+extension;//图片的相对路径,包含文件夹和图片的名字以及文件的扩展名
		logger.debug("current relativeAddr is:"+relativeAddr);//日志信息的添加
		File dest=new File(PathUtil.getImgBasePath()+relativeAddr);//生成文件对象。这个就是绝对路径，包含盘符、目录、文件名称、扩展名。也就是目标文件需要存放在系统的哪一个位置
		logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);
		try {/*.watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"timg.jpg")),0.25f).outputQuality(0.9f)*/
			Thumbnails.of(thumbnail.getImage())
			.size(334, 640)
			.toFile(dest);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;//返回图片的路径，因为shop中的有一个字段用来存放图片地址。返回的是相对路径，适配不同的系统上
	}
	/*
	 * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数 
	 * @return 
	 * */
	public static String getRandomFileName() {
		//获取随机的五位数
		int rannum=r.nextInt(89999)+10000;//获取五位随机数
		String nowTimeStr=sDateFormat.format(new Date());//获取当前时间并转换成年月日时分秒格式，时间越准确，名字越不会重复
		return nowTimeStr+rannum;//拼接起来返回字符串
	}
	
	/*
	 * 获取文件输入流的扩展名
	 * @paramthumbnail
	 * @return
	 * */
	private static String getFileExtension(String fileName) {
		//String originalFileName=thumbnail.getName();
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/*
	 *创建目标路径所涉及到的目录，
	 *即E:/Users/o2o_image/xxx.jpg,那么Users、o2o_image这两个文件夹都得自动创建
	 *@param targetAddr*/
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath=PathUtil.getImgBasePath()+targetAddr;//目标文件所属的全路径。系统路径加上需要存放的目录路径
		File dirPath=new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();//如果目录不存在则进行创建，存在则不创建。进行递归创建文件夹
		}
	}
	
	//删除图片
	public static void deleteFileOrPath(String storePath) {
		File file=new File(PathUtil.getImgBasePath()+storePath);
		if(file.exists()) {
			if(file.isDirectory()) {//如果是文件夹则删除里面的所有文件
				File[] files=file.listFiles();
				for(int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}
			file.delete();//最终将文件或者文件夹删除
		}
	} 
	
	//test
	public static void main(String[]args) throws IOException {//需要抛出异常 //测试，用来测试图片处理工具的使用。
		/*
		InputStream is=new FileInputStream(new File("E:/Images/yellow.jpg"));
		ImageHolder ih=new ImageHolder();
		ih.setImage(is);
		ih.setImageName("yellow.jpg");
		ImageUtil.generateThumbnail(ih, PathUtil.getPileImagePath(1));
		System.out.println("完成");
		*/
	}
}

