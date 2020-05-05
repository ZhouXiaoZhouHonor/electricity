package com.ze.zhou.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ze.zhou.entity.Notice;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public interface NoticeDao {
	//获取公告列表
	List<Notice> queryNotice(@Param("rowIndex")int rowIndex,
			@Param("pageSize")int pageSize);
	//获取公告信息总数
	int queryNoticeCount();
	//获取可用公告
	List<Notice> queryNoticeEnable();
	//添加公告信息，主要是图片
	int insertNotice(Notice notice);
	//更新公告
	int updateNotice(Notice notice);
}
