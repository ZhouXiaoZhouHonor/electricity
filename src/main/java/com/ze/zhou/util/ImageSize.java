package com.ze.zhou.util;
/*
	author:zhouze
	@createTime:2020年5月1日
	@goal:
*/
public enum ImageSize {
	IMAGE_PILE(200,200),
	IMAGE_OPERATOR(200,200),
	IMAGE_USER(80,80),
	IMAGE_PROBLEM(200,200),
	IMAGE_NOTICE(200,200);
	private int width;
	private int height;
	
	private ImageSize(int width,int height) {
		this.height=height;
		this.width=width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}	
}
