package com.ze.zhou.util;

import java.io.InputStream;

/*
	author:zhouze
	@createTime:2020年3月5日
	@goal:
*/
public class ImageHolder {
	
	private String imageName;
	private InputStream image;
	
	public ImageHolder() {
		
	}
	
	public ImageHolder(String imageName,InputStream image) {
		this.imageName=imageName;
		this.image=image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
}
