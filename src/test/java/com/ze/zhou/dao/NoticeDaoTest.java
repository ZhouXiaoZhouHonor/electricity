package com.ze.zhou.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ze.zhou.BaseTest;
import com.ze.zhou.entity.Notice;

/*
	author:zhouze
	@createTime:2020年4月27日
	@goal:
*/
public class NoticeDaoTest extends BaseTest{
	
	@Autowired
	private NoticeDao noticeDao;
	
	@Test
	@Ignore
	public void updateNoticeTest() {
		Notice notice=new Notice();
		notice.setNoticeId(4);
		notice.setNoticeEnableStatus(1);
		notice.setLastEditTime(new Date());
		notice.setNoticePriority(4);
		int effectNum=noticeDao.updateNotice(notice);
		System.out.println(effectNum);
	}
	
	@Test
	@Ignore
	public void insertNoticeTest() {
		Notice notice=new Notice();
		notice.setNoticeEnableStatus(1);
		notice.setCreateTime(new Date());
		notice.setNoticePriority(4);
		notice.setNoticeImg("/notice/3.jpg");
		notice.setNoticeLink("/notice/3.jpg");
		int effectNum=noticeDao.insertNotice(notice);
		System.out.println(effectNum);
	}
	@Test
	@Ignore
	public void queryNoticeTest() {
		int pageSize=noticeDao.queryNoticeCount();
		List<Notice> noticeList=noticeDao.queryNotice(0,pageSize);
		System.out.println(noticeList.size());
	}
	
}
