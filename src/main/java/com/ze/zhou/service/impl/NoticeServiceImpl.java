package com.ze.zhou.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.NoticeDao;
import com.ze.zhou.dto.NoticeExecution;
import com.ze.zhou.entity.Notice;
import com.ze.zhou.enums.NoticeStateEnum;
import com.ze.zhou.service.NoticeService;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public List<Notice> getQueryNotice() {
		return noticeDao.queryNotice();
	}

	@Override
	public NoticeExecution addNotice(Notice notice) {
		NoticeExecution ne=new NoticeExecution();
		if(notice!=null) {
			notice.setCreateTime(new Date());
			notice.setLastEditTime(new Date());
			notice.setNoticeEnableStatus(1);
			int effectNum=noticeDao.insertNotice(notice);
			if(effectNum>0) {
				ne.setState(NoticeStateEnum.SUCCESS.getState());
			}
		}else {
			ne.setState(NoticeStateEnum.NULL_NOTICE.getState());
			ne.setStateInfo("empty notice");
		}
		return ne;
	}

	@Override
	public NoticeExecution changeNotice(Notice notice) {
		NoticeExecution ne=new NoticeExecution();
		if(notice!=null) {
			notice.setLastEditTime(new Date());
			int effectNum=noticeDao.updateNotice(notice);
			if(effectNum>0) {
				ne.setState(NoticeStateEnum.SUCCESS.getState());
			}
		}else {
			ne.setState(NoticeStateEnum.NULL_NOTICE.getState());
			ne.setStateInfo("empty notice");
		}
		return ne;
	}
}