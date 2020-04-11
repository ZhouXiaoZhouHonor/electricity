package com.ze.zhou.util;
/*
	author:zhouze
	@createTime:2020年2月29日
	@goal:求得分页的起始页
*/
public class PageCalculator {
	public static int calculateRowIndex(int pageIndex,int pageSize) {
		return (pageIndex>0)?(pageIndex-1)*pageSize:0;
	}
}
