package com.ze.zhou.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ze.zhou.dao.PileDao;
import com.ze.zhou.dto.PileExecution;
import com.ze.zhou.entity.Pile;
import com.ze.zhou.enums.PileStateEnum;
import com.ze.zhou.exceptions.PileOperationException;
import com.ze.zhou.service.PileService;
import com.ze.zhou.util.ImageHolder;
import com.ze.zhou.util.ImageUtil;
import com.ze.zhou.util.PageCalculator;
import com.ze.zhou.util.PathUtil;

import ch.qos.logback.classic.Logger;

/*
	author:zhouze
	@createTime:2020年4月1日
	@goal:
*/
@Service
public class PileServiceImpl implements PileService{

	Logger logger=(Logger) LoggerFactory.getLogger(PileServiceImpl.class);
	@Autowired
	private PileDao pileDao;
	
	//根据id获取充电桩
	@Override
	public Pile getPileById(Long pileId) {
		// TODO Auto-generated method stub
		return pileDao.queryPileById(pileId);
	}
	
	/*
	 * 在service层需要将一些默认参数填写到对象中*/
	@Override
	public PileExecution addPile(Pile pile,ImageHolder imageHolder) {
		// TODO Auto-generated method stub
		if(pile==null) {//pile为空则返回状态
			return new PileExecution(PileStateEnum.NULL_PILE);
		}
		//设置默认参数
		pile.setCreateTime(new Date());
		pile.setLastEditTime(new Date());
		pile.setPileEnableStatus(1);//创建完成，默认充电桩处于可用状态
		int effectNum=pileDao.insertPile(pile);//将数据插入数据库中
		if(effectNum>0) {//添加成功
			//将文件存在对应的文件夹下面
			logger.info("插入数据成功");
			try {
				if(imageHolder.getImage()!=null) {
					logger.info("开始添加图片到指定目录下");
					addPileImg(pile,imageHolder);
				}
			}catch(Exception e) {
				logger.error("添加图片失败");
				throw new PileOperationException("add pileImg error:"+e.getMessage());
			}
			effectNum=pileDao.updatePile(pile);
			logger.info("成功刷新:"+effectNum);
			if(effectNum<0) {
				throw new PileOperationException("图片更新失败");
			}
			return new PileExecution(PileStateEnum.CHECK,pile);
		}else {
			return new PileExecution(PileStateEnum.INNER_ERROR,pile);
		}
		
	}
	
	//将真正的图片添加到指定目录下并将真实的地址返回到pile对象中
	private void addPileImg(Pile pile,ImageHolder imageholder) {
		String dest=PathUtil.getPileImagePath(pile.getPileId());
		String pileImgAddr=ImageUtil.generateThumbnail(imageholder, dest);
		pile.setPileImg(pileImgAddr);
	}

	
	@Override
	public PileExecution modifyPile(Pile pile, ImageHolder thumbnail) {
		// TODO Auto-generated method stub
		if(pile==null||pile.getPileId()==null) {
			return new PileExecution(PileStateEnum.NULL_PILE);
		}
		//PileExecution pe=new PileExecution();
		try {
			//判断是否需要对图片进行更新
			if(thumbnail.getImage()!=null&&thumbnail.getImageName()!=null&&!"".equals(thumbnail.getImageName())) {
				Pile tempPile=pileDao.queryPileById(pile.getPileId());
				if(tempPile.getPileImg()!=null) {
					ImageUtil.deleteFileOrPath(tempPile.getPileImg());
				}
				addPileImg(pile,thumbnail);
			}
			//更新店铺信息
			pile.setLastEditTime(new Date());
			int effectNum=pileDao.updatePile(pile);
			if(effectNum>0) {
				return new PileExecution(PileStateEnum.SUCCESS,pile);
			}else {
				return new PileExecution(PileStateEnum.INNER_ERROR);
			}
		}catch(Exception e) {
			throw new PileOperationException("errMsg"+e.getMessage());
		}
	}

	//获取充电桩的集合列表
	/*
	 * @Param pile
	 * @Return pileExecution*/
	@Override
	public PileExecution getPileList(Pile pile,int pageIndex,int pageSize) {
		//分页功能的实现
		//获得起始位置和需要获取的数量
		int rowIndex = 0;
		if(pageIndex==-1&&pageSize==-1) {
			pageIndex=0;
			pageSize=-1;
		}else {
			rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		} 
		List<Pile> pileList=null;
		int pileCount=0;
		PileExecution pe=new PileExecution();
		pileList=pileDao.queryPileList(pile,rowIndex,pageSize);
		pileCount=pileDao.queryPileCount(pile);
		if(pileList!=null) {
			pe.setPileList(pileList);
			pe.setCount(pileCount);
		}else {
			pe.setState(PileStateEnum.NULL_PILE.getState());
		}
		return pe;
	}

	//获取条件下充电桩的总数
	@Override
	public int getQueryPileListCount(Pile pile) {
		// TODO Auto-generated method stub
		return pileDao.queryPileCount(pile);
	}
}
