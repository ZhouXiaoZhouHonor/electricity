package com.ze.zhou.service;

import java.util.List;

import com.ze.zhou.dto.NoticeExecution;
import com.ze.zhou.entity.Notice;
import com.ze.zhou.util.ImageHolder;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public interface NoticeService {
	//获取notice
	NoticeExecution getQueryNotice(int pageIndex,int PageSize);
	//获取notice总数
	int getQueryNoticeCount();
	//添加notice
	NoticeExecution addNotice(Notice notice,ImageHolder imageHolder1,ImageHolder imageHolder2);
	//更新notice
	NoticeExecution changeNotice(Notice notice);
	//获取可用notice
	NoticeExecution getQueryNoticeEnable();
}
