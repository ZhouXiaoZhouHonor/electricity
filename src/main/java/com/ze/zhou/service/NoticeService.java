package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.dto.NoticeExecution;
import com.ze.zhou.entity.Notice;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public interface NoticeService {
	//获取notice
	List<Notice> getQueryNotice();
	//添加notice
	NoticeExecution addNotice(Notice notice);
	//更新notice
	NoticeExecution changeNotice(Notice notice);
}
