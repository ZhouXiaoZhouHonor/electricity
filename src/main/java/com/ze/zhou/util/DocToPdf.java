package com.ze.zhou.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

/*
	author:zhouze
	@createTime:2020年6月3日
	@goal:
*/
public class DocToPdf {
	public static boolean getLicense() {
		boolean result = false;
		try {
			//license.xml应放在..\WebRoot\WEB-INF\classes路径下
			InputStream is = DocToPdf.class.getClassLoader().getResourceAsStream("license.xml"); 
			//System.out.println(is==null);
		    License aposeLic = new License();
		    aposeLic.setLicense(is);
		    result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void doc2pdf(String Address) {
		// 验证License 若不验证则转化出的pdf文档会有水印产生
		if(!getLicense()) {
			return;
		}
		//通过文件名将pdf文件名显示出来
		String pdfName=Address.replaceAll(".doc", ".pdf");
		//System.out.println(pdfName);
		try {
			//long old = System.currentTimeMillis();
		    File file = new File(pdfName);  //新建一个空白pdf文档
		    FileOutputStream os = new FileOutputStream(file);
		    Document doc = new Document(Address);                    //Address是将要被转化的word文档
		    doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
		    //long now = System.currentTimeMillis();
	        //System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");  //转化用时
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//System.out.println( getLicense());
		doc2pdf("E:/Users/electricity_image/electricity_upload/report23/2020060214340142210.doc");
	}
}
