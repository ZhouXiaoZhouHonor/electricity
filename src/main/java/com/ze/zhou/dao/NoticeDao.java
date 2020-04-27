package com.ze.zhou.dao;

import java.util.List;

import com.ze.zhou.entity.Notice;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public interface NoticeDao {
	//获取公告列表
	List<Notice> queryNotice();
	//添加公告信息，主要是图片
	int insertNotice(Notice notice);
	//更新公告
	int updateNotice(Notice notice);
}
