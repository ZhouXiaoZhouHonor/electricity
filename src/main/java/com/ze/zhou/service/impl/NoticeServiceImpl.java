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
import com.ze.zhou.util.ImageHolder;
import com.ze.zhou.util.ImageSize;
import com.ze.zhou.util.ImageUtil;
import com.ze.zhou.util.PageCalculator;
import com.ze.zhou.util.PathUtil;

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
	public NoticeExecution getQueryNotice(int pageIndex,int pageSize) {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Notice> noticeList=noticeDao.queryNotice(rowIndex, pageSize);
		int count=noticeDao.queryNoticeCount();
		NoticeExecution ne=new NoticeExecution();
		if(noticeList!=null&&noticeList.size()>0&&count>0) {
			ne.setNoticeList(noticeList);
			ne.setCount(count);
		}
		return ne;
	}

	@Override
	public NoticeExecution addNotice(Notice notice,ImageHolder imageHolder1,ImageHolder imageHolder2) {
		NoticeExecution ne=new NoticeExecution();
		if(notice!=null) {
			notice.setCreateTime(new Date());
			notice.setLastEditTime(new Date());
			notice.setNoticeEnableStatus(1);
			int effectNum=noticeDao.insertNotice(notice);
			if(effectNum>0) {
				//更新图片
				String dest=PathUtil.getNoticeImagePath(notice.getNoticeId());
				String noticeImg=ImageUtil.generateThumbnail(imageHolder1, dest, ImageSize.IMAGE_NOTICE);
				String noticeLink=ImageUtil.generateThumbnail(imageHolder2, dest, ImageSize.IMAGE_NOTICE_LINK);
				notice.setNoticeImg(noticeImg);
				notice.setNoticeLink(noticeLink);
				int result=noticeDao.updateNotice(notice);
				if(result>0) {
					ne.setState(NoticeStateEnum.SUCCESS.getState());
				}else {
					ne.setState(NoticeStateEnum.NULL_NOTICEID.getState());
				}
			}else {
				ne.setState(NoticeStateEnum.INNER_ERROR.getState());
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
			}else {
				ne.setState(NoticeStateEnum.INNER_ERROR.getState());
			}
		}else {
			ne.setState(NoticeStateEnum.NULL_NOTICE.getState());
			ne.setStateInfo("empty notice");
		}
		return ne;
	}

	@Override
	public int getQueryNoticeCount() {
		return noticeDao.queryNoticeCount();
	}

	@Override
	public NoticeExecution getQueryNoticeEnable() {
		NoticeExecution ne=new NoticeExecution();
		List<Notice> noticeList=noticeDao.queryNoticeEnable();
		if(noticeList!=null&&noticeList.size()>0) {
			ne.setNoticeList(noticeList);
			ne.setState(NoticeStateEnum.SUCCESS.getState());
		}else {
			ne.setState(NoticeStateEnum.NULL_NOTICE.getState());
		}
		return ne;
	}
}

